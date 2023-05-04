resource "helm_release" "postgres" {
  name      = "postgres"
  namespace = kubernetes_namespace.polystore_namespace.metadata[0].name

  chart  = "../../../charts/postgresql/chart"
  values = ["${file("../../../charts/postgresql/prod.values.yaml")}"]

  depends_on = [
    kubernetes_namespace.polystore_namespace,
    helm_release.sealed-secrets
  ]
}
