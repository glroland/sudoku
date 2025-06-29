FROM registry.access.redhat.com/ubi9/python-312:1-1749631862

# Swithc to root user for build process
#USER root

# Install Dependencies

# Set the working directory in the container
WORKDIR /opt/app-root

# Copy the dependencies file to the working directory
COPY requirements.txt .

# Install any dependencies
RUN pip install -r requirements.txt

# Copy the content of the local src directory to the working directory
COPY ./src/ .

# By default, listen on port 8080
EXPOSE 8080/tcp

# Switch back to normal run as user
USER 1001

# Specify the command to run on container start
CMD [ "python", "app.py" ]
