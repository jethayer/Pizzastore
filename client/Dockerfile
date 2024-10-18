# Use an official Node.js runtime as a parent image
FROM node:18-alpine

# Set the working directory in the container
WORKDIR /app

# Copy package.json and package-lock.json (if available)
COPY package*.json ./

# Install dependencies
RUN npm install

# Copy the rest of the application code
COPY . .

# Build the app for production using Vite
RUN npm run build

# Use a minimal server to serve the static files
RUN npm install -g serve

# Expose the port serve will run on (change this to 5173 to align with Vite's dev port)
EXPOSE 5173

# Start the app using serve to serve the 'dist' directory (Vite's output folder)
CMD ["serve", "-s", "dist", "-l", "5173"]

