FROM continuumio/miniconda3:23.10.0-1

LABEL name="sudoku-ai-miniconda3" \
    summary="Sudoku AI Python Environment" \
    description="Sudoku AI Python Environment for development and servicing" \
    io.k8s.display-name="Sudoku AI Python Environment" \
    io.k8s.description="Sudoku AI Python Environment for development and servicing" \
    authoritative-source-url="https://github.com/glroland/sudoku" 

RUN mkdir -p /opt/app-root/src /opt/app-root/bin
WORKDIR /opt/app-root/bin

COPY environment.yml /opt/app-root/bin
RUN /opt/conda/bin/conda env create --file /opt/app-root/bin/environment.yml

# Disable announcement plugin of jupyterlab
RUN /opt/conda/envs/sudoku-ai/bin/jupyter labextension disable "@jupyterlab/apputils-extension:announcements"

# Replace Notebook's launcher, "(ipykernel)" with Python's version 3.x.y
RUN sed -i -e "s/Python.*/$(/opt/conda/envs/sudoku-ai/bin/python --version | cut -d '.' -f-2)\",/" /opt/conda/envs/sudoku-ai/share/jupyter/kernels/python3/kernel.json

COPY start-notebook.sh /opt/app-root/bin

RUN chown -R 1001:0 /opt/app-root && \
    chmod -R g=u /opt/app-root && \
    mkdir -p /.local /.cache && \
    chown -R 1001:0 /.local && \
    chmod -R g=u /.local && \ 
    chown -R 1001:0 /.cache && \
    chmod -R g=u /.cache

USER 1001

CMD ["/bin/bash", "-c", "/opt/app-root/bin/start-notebook.sh"]
