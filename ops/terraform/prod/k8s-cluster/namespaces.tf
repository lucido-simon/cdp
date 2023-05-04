resource "kubernetes_namespace" "polystore_namespace" {
  metadata {
    name = "polystore"
  }
}

resource "kubernetes_namespace" "ingress_nginx_namespace" {
  metadata {
    name = "ingress-nginx"
  }
}
