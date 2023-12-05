FROM continuumio/miniconda3:23.10.0-1

# Install dependencies via Anaconda
RUN mkdir -p /opt/sudoku
COPY environment.yml /opt/sudoku
RUN conda env create -f /opt/sudoku/environment.yml

# Pull the environment name out of the environment.yml
RUN echo "export CONDA_ENV_NAME=$(head -1 /opt/sudoku/environment.yml | cut -d' ' -f2)" >> ~/.bashrc
RUN echo "conda activate \$CONDA_ENV_NAME" >> ~/.bashrc

# Deploy application
COPY gunicorn_config.py /opt/sudoku/gunicorn_config.py
COPY app /opt/sudoku
WORKDIR /opt/sudoku

EXPOSE 8080

CMD /bin/bash -c "source ~/.bashrc && gunicorn --config /opt/sudoku/gunicorn_config.py ocrsvc:app"
