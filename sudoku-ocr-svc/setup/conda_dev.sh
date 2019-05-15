#!/bin/bash

# update anaconda
conda update -y conda

# remove sudoku environment if it already exists
conda remove -y -n sudoku --all

# create and activate environment
source ~/anaconda3/bin/activate sudoku
conda create -y -n sudoku
conda activate sudoku

