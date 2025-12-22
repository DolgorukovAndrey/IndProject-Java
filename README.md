# JavaProjectPrice

БАЗА ДАННЫХ:

CREATE USER java_user WITH PASSWORD 'test123';
GRANT ALL PRIVILEGES ON DATABASE javaproject TO java_user;
GRANT ALL PRIVILEGES ON SCHEMA public TO java_user;
GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA public TO java_user;
ALTER SCHEMA public OWNER TO java_user;

CREATE TABLE Services (
    id_service SERIAL PRIMARY KEY,
    service_name TEXT NOT NULL,
	service_info TEXT NOT NULL
);

CREATE TABLE Carwashes (
    id_carwash SERIAL PRIMARY KEY,
    carwash_name TEXT NOT NULL,
	carwash_address TEXT NOT NULL
);

CREATE TABLE Bodytypes (
    id_bodytype SERIAL PRIMARY KEY,
    bodytype_name TEXT NOT NULL
);

CREATE TABLE ServicesPrice (
	id_serviceprice SERIAL PRIMARY KEY,
	id_service INTEGER REFERENCES Services(id_service) ON DELETE CASCADE,
	id_carwash INTEGER REFERENCES Carwashes(id_carwash) ON DELETE CASCADE,
	id_bodytype INTEGER REFERENCES Bodytypes(id_bodytype) ON DELETE CASCADE,
	leadtime INTEGER NOT NULL,
	price INTEGER NOT NULL, 
	price_date DATE NOT NULL,
	UNIQUE(id_service, id_carwash, id_bodytype, price_date)
);

CREATE VIEW AllPrice AS SELECT 
c.carwash_name, c.carwash_address, service_name, service_info, b.bodytype_name, sp.leadtime, sp.price, sp.price_date
FROM ServicesPrice sp
JOIN Bodytypes b ON b.id_bodytype = sp.id_bodytype
JOIN Carwashes c ON c.id_carwash = sp.id_carwash
JOIN Services s ON s.id_service = sp.id_service


CREATE OR REPLACE FUNCTION insert_into_allprice()
RETURNS TRIGGER AS $$
DECLARE
    v_service_id INTEGER;
    v_carwash_id INTEGER;
    v_bodytype_id INTEGER;
BEGIN
    SELECT id_service INTO v_service_id FROM Services WHERE service_name = New.service_name;
	IF v_service_id IS NULL THEN
        INSERT INTO Services (service_name, service_info) 
        VALUES (NEW.service_name, NEW.service_info)
        RETURNING id_service INTO v_service_id;
    END IF;
    SELECT id_carwash INTO v_carwash_id FROM Carwashes WHERE carwash_name = New.carwash_name;
	IF v_carwash_id IS NULL THEN
        INSERT INTO Carwashes (carwash_name, carwash_address) 
        VALUES (NEW.carwash_name, NEW.carwash_address)
        RETURNING id_carwash INTO v_carwash_id;
    END IF;
    SELECT id_bodytype INTO v_bodytype_id FROM Bodytypes WHERE bodytype_name = New.bodytype_name;
	IF v_bodytype_id IS NULL THEN
        INSERT INTO Bodytypes (bodytype_name) 
        VALUES (NEW.bodytype_name)
        RETURNING id_bodytype INTO v_bodytype_id;
    END IF;
    INSERT INTO ServicesPrice (id_service, id_carwash, id_bodytype, leadtime, price, price_date)
    VALUES (v_service_id, v_carwash_id, v_bodytype_id, NEW.leadtime, NEW.price, NEW.price_date);
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER insert_allprice_trigger
    INSTEAD OF INSERT ON AllPrice
    FOR EACH ROW
    EXECUTE FUNCTION insert_into_allprice();


CREATE OR REPLACE FUNCTION update_into_allprice()
RETURNS TRIGGER AS $$
DECLARE
    v_service_id INTEGER;
    v_carwash_id INTEGER;
    v_bodytype_id INTEGER;
BEGIN
    SELECT id_service INTO v_service_id FROM Services WHERE service_name = OLD.service_name;
	UPDATE Services SET service_name = NEW.service_name, service_info = NEW.service_info
	WHERE id_service = v_service_id;
    SELECT id_carwash INTO v_carwash_id FROM Carwashes WHERE carwash_name = OLD.carwash_name;
	UPDATE Carwashes SET carwash_name = NEW.carwash_name, carwash_address = NEW.carwash_address
	WHERE id_carwash = v_carwash_id;
    SELECT id_bodytype INTO v_bodytype_id FROM Bodytypes WHERE bodytype_name = OLD.bodytype_name;
	UPDATE Bodytypes SET bodytype_name = NEW.bodytype_name WHERE id_bodytype = v_bodytype_id;
    UPDATE ServicesPrice SET leadtime = NEW.leadtime, price = NEW.price, price_date = NEW.price_date
    WHERE id_service = v_service_id AND id_carwash = v_carwash_id AND id_bodytype = v_bodytype_id;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER update_allprice_trigger
    INSTEAD OF UPDATE ON AllPrice
    FOR EACH ROW
    EXECUTE FUNCTION update_into_allprice();
	

CREATE OR REPLACE FUNCTION delete_from_allprice()
RETURNS TRIGGER AS $$
DECLARE
    v_service_id INTEGER;
    v_carwash_id INTEGER;
    v_bodytype_id INTEGER;
BEGIN
    SELECT id_service INTO v_service_id FROM Services WHERE service_name = OLD.service_name;
    SELECT id_carwash INTO v_carwash_id FROM Carwashes WHERE carwash_name = OLD.carwash_name;
    SELECT id_bodytype INTO v_bodytype_id FROM Bodytypes WHERE bodytype_name = OLD.bodytype_name;
    DELETE FROM ServicesPrice 
    WHERE id_service = v_service_id AND id_carwash = v_carwash_id AND id_bodytype = v_bodytype_id;
    RETURN OLD;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER delete_allprice_trigger
    INSTEAD OF DELETE ON AllPrice
    FOR EACH ROW
    EXECUTE FUNCTION delete_from_allprice();

GRANT ALL PRIVILEGES ON ALL SEQUENCES IN SCHEMA public TO java_user;

ALTER DEFAULT PRIVILEGES IN SCHEMA public 
GRANT ALL PRIVILEGES ON SEQUENCES TO java_user;

CREATE OR REPLACE FUNCTION get_actual_from_allprice()
RETURNS SETOF AllPrice AS $$
BEGIN
    RETURN QUERY
    SELECT DISTINCT ON (ap.carwash_name, ap.carwash_address, ap.service_name, ap.bodytype_name)
        ap.carwash_name,
        ap.carwash_address,
        ap.service_name,
        ap.service_info,
        ap.bodytype_name,
        ap.leadtime,
        ap.price,
        ap.price_date
    FROM AllPrice ap
    ORDER BY ap.carwash_name, ap.carwash_address, ap.service_name, ap.bodytype_name,
             ap.price_date DESC;
END;
$$ LANGUAGE plpgsql;
