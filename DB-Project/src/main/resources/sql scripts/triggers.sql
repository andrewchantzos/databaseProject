/*1st Trigger: audit changes in doctors table
with auxiliary table
*/
DELIMITER $$
CREATE TRIGGER before_doctors_update
    BEFORE UPDATE ON doctors
    FOR EACH ROW 
BEGIN
    INSERT INTO doctors_audit
    SET action = 'update',
    	old_name = OLD.lastname,
        changedate = NOW(); 
END$$
DELIMITER ;


DELIMITER $$
CREATE TRIGGER before_doctors_delete
    BEFORE delete ON doctors
    FOR EACH ROW 
BEGIN
    INSERT INTO doctors_audit
    SET action = 'delete',
    	old_name = OLD.lastname,
        changedate = NOW(); 
END$$


/*2nd Trigger: implement the on update cascade 
 on doctors table
*/
DELIMITER $$
CREATE TRIGGER update_patients AFTER UPDATE ON doctors FOR EACH ROW
BEGIN
IF NEW.doctor_id != OLD.doctor_id THEN

  UPDATE patients pt
     SET /* the same logic that's in the before update trigger on the child table */
    pt.doctor_doctor_id = NEW.doctor_id;

END IF;
END $$
DELIMITER ;


DELIMITER $$
CREATE TRIGGER before_pharmacy
    BEFORE UPDATE ON pharmacies
    FOR EACH ROW 
BEGIN
    INSERT INTO pharmacies_audit
    SET action = 'update',
		modified_id = OLD.pharmacy_id,
    	old_name = OLD.name,
		postal_code = OLD.postal_code,
		phone = OLD.phone,
		street = OLD.street,
        changedate = NOW(); 
END $$
DELIMITER ;
