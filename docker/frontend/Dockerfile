FROM nginx:alpine

#config
COPY ./front/nginx.conf /etc/nginx/nginx.conf

#static
COPY ./front/index.html /usr/share/nginx/html/
COPY ./front/index.js /usr/share/nginx/html/
COPY ./front/style.css /usr/share/nginx/html/
COPY ./front/images /usr/share/nginx/html/images