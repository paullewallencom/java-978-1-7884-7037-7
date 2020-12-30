FROM node:8

RUN mkdir /app

WORKDIR /app

ADD . /app

RUN npm i

EXPOSE 8080

CMD ["node", "index.js"]