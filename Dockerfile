FROM azul/zulu-openjdk:17.0.6-jre-headless

# Install dependencies
RUN apt-get update && \
    apt-get install -y wget gnupg2 ca-certificates && \
    rm -rf /var/lib/apt/lists/*

# Install Google Chrome
RUN wget -q -O - https://dl.google.com/linux/linux_signing_key.pub | apt-key add - && \
    echo "deb [arch=amd64] http://dl.google.com/linux/chrome/deb/ stable main" >> /etc/apt/sources.list.d/google-chrome.list && \
    apt-get update && \
    apt-get install -y google-chrome-stable && \
    rm -rf /var/lib/apt/lists/*

# Install ChromeDriver
RUN wget -q -O - https://dl.google.com/linux/linux_signing_key.pub | apt-key add - && \
    echo "deb [arch=amd64] http://dl.google.com/linux/chrome/deb/ stable main" >> /etc/apt/sources.list.d/google-chrome.list && \
    apt-get update && \
    apt-get install -yqq unzip && \
    LATEST=$(wget -q -O - http://chromedriver.storage.googleapis.com/LATEST_RELEASE) && \
    wget http://chromedriver.storage.googleapis.com/$LATEST/chromedriver_linux64.zip && \
    unzip chromedriver_linux64.zip && \
    mv chromedriver /usr/bin/chromedriver && \
    chmod +x /usr/bin/chromedriver && \
    rm chromedriver_linux64.zip && \
    rm -rf /var/lib/apt/lists/*

# Set display environment variable (for running Chrome in headless mode)
ENV PROFILE=prod
ENV DISPLAY=:99

# Expose ports (if necessary)
EXPOSE 8080

# Set working directory (if necessary)
WORKDIR /app

# Copy application files (if necessary)
COPY target/siakad-presence-automation-0.0.1-SNAPSHOT.jar /app/app.jar

# Start application (if necessary)
ENTRYPOINT [ "google-chrome --version" ]
CMD [ "java","-jar","app.jar" ]





