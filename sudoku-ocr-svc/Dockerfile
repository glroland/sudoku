FROM continuumio/miniconda3:23.10.0-1

# Install dependencies via Anaconda
RUN mkdir -p /opt/sudoku
COPY setup/conda_packages.sh /opt/sudoku
RUN /opt/sudoku/conda_packages.sh

# Deploy application
COPY gunicorn_config.py /opt/sudoku/gunicorn_config.py
COPY app /opt/sudoku
WORKDIR /opt/sudoku

EXPOSE 8080

CMD ["gunicorn", "--config", "/opt/sudoku/gunicorn_config.py", "ocrsvc:app"]

