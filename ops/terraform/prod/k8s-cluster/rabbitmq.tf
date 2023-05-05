resource "helm_release" "rabbitmq" {
  name      = "rabbitmq"
  namespace = kubernetes_namespace.polystore_namespace.metadata[0].name

  chart  = "../../../charts/rabbitmq/chart"
  values = ["${file("../../../charts/rabbitmq/prod.values.yaml")}"]

  depends_on = [
    kubernetes_namespace.polystore_namespace,
  ]
}
