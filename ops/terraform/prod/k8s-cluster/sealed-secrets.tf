resource "helm_release" "sealed-secrets" {
  name      = "sealed-secrets-controller"
  namespace = "kube-system"

  chart  = "../../../charts/sealed-secrets"
  values = ["${file("../../../charts/sealed-secrets/values.yaml")}"]

  provisioner "local-exec" {
    working_dir = "../secrets"
    command     = "node ./seal.js --apply"
  }

}

