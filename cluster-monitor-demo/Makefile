.PHONY: build clean-all clean-containers clean-images docker-build docker-up gradle-clean help setup teardown

# Build fat JAR using Gradle
build:
	./gradlew shadowJar

# Clean all: containers and images
clean-all: clean-containers clean-images

# Remove all containers (force remove running + volumes)
clean-containers:
	docker rm -vf $$(docker ps -aq)

# Remove all images (force)
clean-images:
	docker rmi -f $$(docker images -aq)

# Build Docker image from Dockerfile
docker-build:
	docker-compose build

# Bring up services with Docker Compose
docker-up:
	docker-compose up --build -d

gradle-clean:
	./gradlew clean

# Show help message
help:
	@echo ""
	@echo "Available commands:"
	@echo "  build            - Build fat JAR using Gradle (shadowJar)"
	@echo "  clean-all        - Remove all Docker containers and images"
	@echo "  clean-containers - Remove all Docker containers (and volumes)"
	@echo "  clean-images     - Remove all Docker images"
	@echo "  docker-build     - Build Docker image using docker-compose"
	@echo "  docker-up        - Run services using docker-compose"
	@echo "  gradle-clean     - Run ./gradlew clean"
	@echo "  setup            - Build fat JAR, Docker image, and run with Compose"
	@echo "  teardown         - Clean Gradle build + all Docker containers/images"
	@echo "  help             - Show this help message"
	@echo ""

teardown: clean-containers clean-images gradle-clean

setup: build docker-build docker-up

resetup: teardown setup
