{{- define "user-service.fullname" -}}
{{- .Release.Name | trunc 63 | trimSuffix "-" }}
{{- end }}

{{- define "user-service.labels" -}}
app.kubernetes.io/name: {{ include "user-service.fullname" . }}
app.kubernetes.io/instance: {{ .Release.Name }}
{{- end }}

{{- define "user-service.selectorLabels" -}}
app: {{ include "user-service.fullname" . }}
{{- end }}