resource "helm_release" "sealed-secrets" {
  name      = "sealed-secrets"
  namespace = kubernetes_namespace.sealed_secrets_namespace.metadata[0].name

  chart  = "../../../charts/sealed-secrets"
  values = ["${file("../../../charts/sealed-secrets/values.yaml")}"]

  depends_on = [
    kubernetes_namespace.sealed_secrets_namespace,
  ]
}
