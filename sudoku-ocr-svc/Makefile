clean:
	find . -name "*.pyc" -exec rm -f {} \;

init:
	conda update -n base -c defaults conda
	conda create -n sudoku
	conda install --yes --file requirements.txt
	conda install -c conda-forge flask-restful 
	conda install -c anaconda flask-cors

ocrtrain:
#	conda init bash
#	conda activate sudoku
	python -m train

run:
#	conda activate sudoku
	python -m ocrsvc

ocrtest:
	python -m unittest discover -s . -p "*_test.py"
