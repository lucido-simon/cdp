resource "helm_release" "redis" {
  name      = "redis"
  namespace = kubernetes_namespace.polystore_namespace.metadata[0].name

  chart  = "../../../charts/redis/chart"
  values = ["${file("../../../charts/redis/prod.values.yaml")}"]

  depends_on = [
    kubernetes_namespace.polystore_namespace,
    helm_release.sealed-secrets
  ]
}
