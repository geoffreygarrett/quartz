{{/* Preamble */}}
{{ $content := .Content }}
{{ $raw := .RawContent }}
{{ $page := .Page }}

{{/* Define regular expressions for pandoc citations */}}
{{/* For pandoc style: https://pandoc.org/MANUAL.html#extension-citations */}}
{{/* For cases: https://regex101.com/r/RWdzDD/1 */}}
{{ $re_items := ".?\\[.*\\].?" }}
{{ $re_item := "\\[?([@\\w\\s,{}.()*-]+);?\\s*([@\\w\\s,{}.()*-]+)*\\]?" }}
{{ $re_item_prefix  := "\\[([\\w\\s]*)\\s@" }}
{{ $re_item_key := "@{(.*)}|@([\\w\\:\\.\\#\\$\\%\\&\\-\\+\\?\\<\\>\\~\\/]+)" }}
{{ $re_item_locator := "\\w+\\.\\s*[\\d-]+" }}
{{ $re_item_suffix := "\\w+\\.\\s*[\\d-]+[\\w]+\\s([\\w\\s*_]+)" }}

{{/* Iterate and parse items into dictionary */}}
{{ $order_mapping := (dict ) }}
{{ $count := 0 }}
{{ $items := $content | findRE $re_items }}
{{ $parsed_items := slice (dict ) }}
{{ range $items }}
    {{ $item_match := . }}
    {{ $item_prefix := $item_match | findRE $re_item_prefix }}
    {{ $item_key_match := $item_match | findRE $re_item_key }}
    {{ $item_locator := $item_match | findRE $re_item_locator }}
    {{ $item_suffix := $item_match | findRE $re_item_suffix }}
    {{/* Extract appropriate groups */}}
    {{ $item_key := replaceRE $item_key_match "$2" $re_item_key }}
    {{ "ooooooooo" }}
    {{ $item_key }}
    {{ "ssssssssss" }}

    {{ $parsed_items = $parsed_items | append (dict
        "prefix" $item_prefix
        "key" $item_key
        "locator" $item_locator
        "suffix" $item_suffix
    )
    }}
    {{/* Count order of occurence */}}
    {{ if isset $order_mapping $item_key }}
    {{ else }}
        {{ $count := add $count 1 }}
        {{ "aaaaaaaaaaaaaaa "}}
        {{ $item_key }}{{ $count }}
        {{ "bbbbbbbbbbbbbbb "}}
    {{ end }}
{{ end }}

{{/* Testing */}}
{{ range $parsed_items }}
{{ if .prefix }}{{ .prefix }}{{ end }}
{{ if .key }}{{ .key }}{{ end }}
{{ if .locator }}{{ .locator }}{{ end }}
{{ if .suffix }}{{ .suffix }}{{ end }}
{{ end }}

{{ $style := "nature" }}

{{/* Citation Mapping */}}
{{$citation_map := (dict )}}
{{$citations := $content | findRE "@(\\w+)"}}
{{range $i, $c := $citations}}
  {{ $i := add $i 1 }}
  {{if isset $citation_map $c}}
  {{else}}
    {{ $i }} {{ $c }}
    {{$citation_map = merge $citation_map (dict $c $i)}}
  {{end}}
{{end}}

{{/* Nature Style */}}
{{$citation_groups := $content | findRE "\\[((.*) )?@(.*?)(, (.*))?\\]"}}
{{range $i, $group := $citation_groups}}
  {{$citations := $group | findRE "@(\\w*)" }}
  {{range $j, $citation := $citations}}
  {{end}}
{{end}}

