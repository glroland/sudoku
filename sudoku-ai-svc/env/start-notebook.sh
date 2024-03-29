#!/usr/bin/env bash

function start_process() {
    trap stop_process TERM INT

    echo "Running command: $@"
    "$@" &

    PID=$!
    wait $PID
    trap - TERM INT
    wait $PID
    STATUS=$?
    exit $STATUS
}

function stop_process() {
    kill -TERM $PID
}

# Initialize notebooks arguments variable
NOTEBOOK_PROGRAM_ARGS=""

# Set default ServerApp.port value if NOTEBOOK_PORT variable is defined
if [ -n "${NOTEBOOK_PORT}" ]; then
    NOTEBOOK_PROGRAM_ARGS+="--ServerApp.port=${NOTEBOOK_PORT} "
fi

# Set default ServerApp.base_url value if NOTEBOOK_BASE_URL variable is defined
if [ -n "${NOTEBOOK_BASE_URL}" ]; then
    NOTEBOOK_PROGRAM_ARGS+="--ServerApp.base_url=${NOTEBOOK_BASE_URL} "
fi

# Set default ServerApp.root_dir value if NOTEBOOK_ROOT_DIR variable is defined
if [ -n "${NOTEBOOK_ROOT_DIR}" ]; then
    NOTEBOOK_PROGRAM_ARGS+="--ServerApp.root_dir=${NOTEBOOK_ROOT_DIR} "
else
    NOTEBOOK_PROGRAM_ARGS+="--ServerApp.root_dir=${HOME} "
fi

# Add additional arguments if NOTEBOOK_ARGS variable is defined
if [ -n "${NOTEBOOK_ARGS}" ]; then
    NOTEBOOK_PROGRAM_ARGS+=${NOTEBOOK_ARGS}
fi

CONDA_ENV_NAME=$(head -1 /opt/app-root/bin/environment.yml | cut -d' ' -f2)
echo Conda Environment Name: $CONDA_ENV_NAME 
echo Current Working Directory: $(pwd)
source /opt/conda/bin/activate /opt/conda/envs/${CONDA_ENV_NAME}

# Start the JupyterLab notebook
start_process jupyter lab ${NOTEBOOK_PROGRAM_ARGS} \
    --ServerApp.ip=0.0.0.0 \
    --ServerApp.allow_origin="*" \
    --ServerApp.open_browser=False
