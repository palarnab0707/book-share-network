#Step-1 We will use our node version
FROM node:22 as build-stage
#Step-2 Create a work directory
WORKDIR /app
#Step-3 copy package json files to the work directory /app
COPY package*.json ./
#Step-4 install the package(create node modules)
RUN npm install
#Step-5 after installation copy everything to working directory /app
COPY . .
#Step-6 build the project on production environment(it may change based on environment)
RUN npm run build --prod
#Step-7 we will use nginx server(Details are there in readme file)
FROM nginx:alpine
#Step-8 Copy our nginx configuration file inside etc/nginx folder
COPY nginx.conf /etc/nginx/nginx.conf
#Step-9 copy our build file to nginx html. After build, folder will create on our project name(project name we can
#get it from package.json).
COPY --from=build-stage /app/dist/book-network-ui /usr/share/nginx/html
#Nginx run on port 80
EXPOSE 80
#Below command is the command for build the image, Need to run it inside our project folder.
#docker build -t bsn/bsn-ui:1.0.0 -f ../../docker/frontend/Dockerfile .