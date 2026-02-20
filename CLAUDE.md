# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Architecture

Full-stack microservices Sudoku platform with 4 services:

- **sudoku-app** — React 18 SPA served via Nginx. Routes: `/`, `/game`, `/options`, `/scan`. The `/scan` route is a 4-step image capture flow (Camera → Upload → Mark → Preview) that integrates with the OCR service.
- **sudoku-svc** — Spring Boot 3 (Java 21) game engine. Handles puzzle generation (`/generate`), solving (`/solve`), move logging relay (`/logMove`), and bulk generation (`/generateBulk`).
- **sudoku-log-svc** — Quarkus 3 (Java 21) REST service backed by PostgreSQL. Persists game sessions and individual moves via `/log/game` and `/log/move`.
- **sudoku-ocr-svc** — FastAPI (Python 3.12) service using TensorFlow/Keras for digit recognition from uploaded images. Endpoints: `/upload`, `/extract`.
- **sudoku-ai-svc** — Python training environment (Jupyter notebooks) for the OCR ML models. Not a runtime service.

All services are containerized (Podman/Docker) and deployed to Kubernetes via Helm charts and ArgoCD GitOps.

## Build Commands

```bash
# Build all Java backend services
make build

# Build all container images (uses podman)
make images

# Individual service builds
cd sudoku-svc && mvn clean package
cd sudoku-log-svc && mvn clean package
cd sudoku-ocr-svc && pip install -r requirements.txt
```

## Frontend (sudoku-app)

```bash
cd sudoku-app
npm start          # Dev server
npm run build      # Production build
npm test           # Jest tests
npm test -- --testPathPattern=<file>  # Single test file
```

## Java Services

```bash
# Run tests only
cd sudoku-svc && mvn test
cd sudoku-log-svc && mvn test

# Run a single test class
cd sudoku-svc && mvn test -Dtest=ClassName
```

## Python Services (sudoku-ocr-svc)

```bash
cd sudoku-ocr-svc
pip install -r requirements.txt
uvicorn src.app:app --reload    # Dev server

# Tests
cd sudoku-ocr-svc && python -m pytest src/test/
```

## Deployment

- **Helm charts**: `deploy/helm/sudoku/` — templated deployments for all services, ConfigMaps, Ingress, HPA
- **ArgoCD**: `deploy/argo-app.yaml` — GitOps sync from this repo's Helm values at `deploy/helm/sudoku/values.yaml`, target namespace `sudoku`
- **Jenkins**: `deploy/Jenkinsfile` — CI pipeline with Maven builds and OWASP security scan stages
- **Raw K8s**: `deploy/k8s/` — direct manifests (used for reference; Helm is the primary deployment method)

Health checks use Spring Actuator at `/actuator/health/readiness` and `/actuator/health/liveness`.

## Key Source Locations

| Concern | Location |
|---|---|
| Frontend routing | `sudoku-app/src/App.js` |
| Frontend config (API URLs) | `sudoku-app/src/AppConfig.js` |
| Game REST controller | `sudoku-svc/src/main/java/.../SudokuController.java` |
| Solver algorithm | `sudoku-svc/src/main/java/.../game/Solver.java` |
| Log REST resource | `sudoku-log-svc/src/main/java/.../LogResource.java` |
| OCR FastAPI app | `sudoku-ocr-svc/src/app.py` |
| OCR core logic | `sudoku-ocr-svc/src/ocrlib/core.py` |
| Helm values | `deploy/helm/sudoku/values.yaml` |
