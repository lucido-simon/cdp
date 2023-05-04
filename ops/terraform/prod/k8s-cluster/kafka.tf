resource "helm_release" "kafka" {
  name      = "kafka"
  namespace = kubernetes_namespace.polystore_namespace.metadata[0].name

  chart  = "../../../charts/kafka/chart"
  values = ["${file("../../../charts/kafka/prod.values.yaml")}"]

  depends_on = [
    kubernetes_namespace.polystore_namespace,
  ]
}
