FROM registry.access.redhat.com/ubi8/ubi
LABEL maintainer="glroland@hotmail.com"

RUN echo 'export PATH=/opt/conda/bin:$PATH' > /etc/profile.d/conda.sh && \
    curl https://repo.anaconda.com/archive/Anaconda3-2021.05-Linux-x86_64.sh -o ~/miniconda.sh && \
    /bin/bash ~/miniconda.sh -f -b -p /opt/conda && \
    rm ~/miniconda.sh

ENV PATH /opt/conda/bin:$PATH

# Install dependencies via Anaconda
RUN mkdir -p /deploy/app
COPY setup/conda_packages.sh /deploy/app
RUN /deploy/app/conda_packages.sh

# Deploy application
COPY gunicorn_config.py /deploy/gunicorn_config.py
COPY app /deploy/app
WORKDIR /deploy/app

# Set Python path
ENV PYTHONPATH=/deploy

EXPOSE 8080

CMD ["gunicorn", "--config", "/deploy/gunicorn_config.py", "ocrsvc:app"]
