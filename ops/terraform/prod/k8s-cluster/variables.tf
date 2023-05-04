variable "kubeconfig_context_name" {
  type    = string
  default = "k3d-polystore"
}

variable "kubeconfig_path" {
  type    = string
  default = "~/.kube/polystore"
}
