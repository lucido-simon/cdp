resource "helm_release" "mongo" {
  name      = "mongo"
  namespace = kubernetes_namespace.polystore_namespace.metadata[0].name

  chart  = "../../../charts/mongo/chart"
  values = ["${file("../../../charts/mongo/prod.values.yaml")}"]

  depends_on = [
    kubernetes_namespace.polystore_namespace,
    helm_release.sealed-secrets
  ]
}
