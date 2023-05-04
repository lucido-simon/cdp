resource "kubernetes_namespace" "polystore_namespace" {
  metadata {
    name = "namespace"
  }
}

resource "kubernetes_namespace" "ingress_nginx_namespace" {
  metadata {
    name = "ingress-nginx"
  }
}

resource "kubernetes_namespace" "sealed_secrets_namespace" {
  metadata {
    name = "sealed-secrets"
  }
}

