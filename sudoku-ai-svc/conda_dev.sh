#!/bin/zsh

#echo ---
#echo --- Preparing Anaconda
#echo ---
#conda activate base
#conda update -y conda
#echo

export CONDA_ENV_NAME=$(head -1 environment.yml | cut -d' ' -f2)
echo ---
echo --- Removing environment if it already exists
echo ---
echo --- conda remove -y -n $CONDA_ENV_NAME --all
echo ---
conda remove -y -n $CONDA_ENV_NAME --all
echo

echo ---
echo --- Create environment
echo ---
echo --- conda env create -f environment.yml
echo ---
conda env create -f environment.yml
echo

echo ---
echo --- Create environment
echo ---
echo --- conda activate $CONDA_ENV_NAME
echo ---
conda activate $CONDA_ENV_NAME
echo
