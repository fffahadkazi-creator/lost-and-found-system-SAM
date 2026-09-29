-- Campus Lost & Found Management System
-- Run this in MySQL Workbench / mysql CLI before running the Java app

CREATE DATABASE IF NOT EXISTS lost_found_db;
USE lost_found_db;

CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    role ENUM('STUDENT', 'ADMIN') NOT NULL DEFAULT 'STUDENT'
);

CREATE TABLE items (
    id INT AUTO_INCREMENT PRIMARY KEY,
    type ENUM('LOST', 'FOUND') NOT NULL,
    item_name VARCHAR(100) NOT NULL,
    category VARCHAR(50) NOT NULL,
    item_date DATE NOT NULL,
    location VARCHAR(100) NOT NULL,
    description TEXT,
    status ENUM('OPEN', 'CLAIMED', 'RESOLVED') NOT NULL DEFAULT 'OPEN',
    reported_by INT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (reported_by) REFERENCES users(id)
);

CREATE TABLE claims (
    id INT AUTO_INCREMENT PRIMARY KEY,
    item_id INT NOT NULL,
    claimant_id INT NOT NULL,
    status ENUM('PENDING', 'APPROVED', 'REJECTED') NOT NULL DEFAULT 'PENDING',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (item_id) REFERENCES items(id),
    FOREIGN KEY (claimant_id) REFERENCES users(id)
);

-- Seed one admin account so you can log in and test approvals
-- Change this password after first login
INSERT INTO users (name, email, password, role)
VALUES ('Admin', 'admin@campus.edu', 'admin123', 'ADMIN');
