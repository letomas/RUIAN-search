FROM node:13.12.0-alpine as build
WORKDIR /app

COPY package*.json ./
RUN npm install
COPY . .
RUN npm run build

FROM nginx:1.17-alpine
COPY --from=build /app/dist /usr/share/nginx/html
COPY ./nginxConfig/conf.d /etc/nginx/conf.d
COPY ./nginxConfig/nginx.conf /etc/nginx/nginx.conf
CMD ["nginx", "-g", "daemon off;"]