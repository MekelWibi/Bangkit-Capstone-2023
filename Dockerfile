FROM node:18.19.0-alpine3.19
WORKDIR /app
COPY . .

ENV DB_HOST=localhost
ENV DB_USER=root
ENV DB_PASSWORD=''
ENV DB_NAME=''

RUN npm install

EXPOSE 8080

CMD [ "node", "app.js" ]