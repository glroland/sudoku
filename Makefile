build:
	cd sudoku-svc && mvn clean package
	cd sudoku-log-svc && mvn clean package

images:
	cd sudoku-app && podman build --platform linux/amd64 --tag sudoku-app:latest .
	cd sudoku-svc && podman build --platform linux/amd64 --tag sudoku-svc:latest .
	cd sudoku-log-svc && podman build --platform linux/amd64 --tag sudoku-log-svc:latest .
	cd sudoku-ocr-svc && podman build --platform linux/amd64 --tag sudoku-ocr-svc:latest .
