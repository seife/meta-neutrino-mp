# the ac_cv_sizeof_off_t=8 warrants this
# ...but PRINC is now deprecated...
#PRINC := "${@int(PRINC) + 1}"

PR .= ".100"

do_configure_prepend() {
	# curl checks this with _FILE_OFFSET_BITS=64...
	export ac_cv_sizeof_off_t=8
	sed -i s:OPT_GNUTLS/bin:OPT_GNUTLS:g ${S}/configure.ac
}
