CREATE TABLE CLINIC (ID BIGINT NOT NULL, NAME VARCHAR(255), PRIMARY KEY (ID))
CREATE TABLE TREATMENT (ID BIGINT NOT NULL, DTYPE VARCHAR(31), PATIENT_ID BIGINT, PRIMARY KEY (ID))
CREATE TABLE PATIENT (ID BIGINT NOT NULL, DOB DATE, NAME VARCHAR(255), TREATMENTDAO BLOB(2147483647), CLINIC_ID BIGINT, PRIMARY KEY (ID))
CREATE TABLE PROVIDER (NPI BIGINT NOT NULL, PRIMARY KEY (NPI))
CREATE TABLE CLINIC_PATIENT (Clinic_ID BIGINT NOT NULL, patients_ID BIGINT NOT NULL, PRIMARY KEY (Clinic_ID, patients_ID))
CREATE TABLE PATIENT_TREATMENT (Patient_ID BIGINT NOT NULL, treatments_ID BIGINT NOT NULL, PRIMARY KEY (Patient_ID, treatments_ID))
ALTER TABLE TREATMENT ADD CONSTRAINT TREATMENTPATIENTID FOREIGN KEY (PATIENT_ID) REFERENCES PATIENT (ID)
ALTER TABLE PATIENT ADD CONSTRAINT PATIENT_CLINIC_ID FOREIGN KEY (CLINIC_ID) REFERENCES CLINIC (ID)
ALTER TABLE CLINIC_PATIENT ADD CONSTRAINT CLNCPATIENTClnicID FOREIGN KEY (Clinic_ID) REFERENCES CLINIC (ID)
ALTER TABLE CLINIC_PATIENT ADD CONSTRAINT CLNCPATIENTptntsID FOREIGN KEY (patients_ID) REFERENCES PATIENT (ID)
ALTER TABLE PATIENT_TREATMENT ADD CONSTRAINT PTNTTREATMENTPtntD FOREIGN KEY (Patient_ID) REFERENCES PATIENT (ID)
ALTER TABLE PATIENT_TREATMENT ADD CONSTRAINT PTNTTRTMNTtrtmntsD FOREIGN KEY (treatments_ID) REFERENCES TREATMENT (ID)