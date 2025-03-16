create user sudoku_app with password 'sudoku123';
ALTER USER sudoku_app WITH SUPERUSER;
create database sudoku_db with owner sudoku_app;

