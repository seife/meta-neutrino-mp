# CURLGNUTLS does no longer exist since yocto daisy,
# but tripledragon is stuck at dizzy anyway :-)
# and on triple, don't want it... everywhere else it's ok.
CURLGNUTLS_tripledragon = "--without-gnutls --without-ssl"

# the ac_cv_sizeof_off_t=8 warrants this
# ...but PRINC is now deprecated...
#PRINC := "${@int(PRINC) + 1}"

do_configure_prepend() {
	# curl checks this with _FILE_OFFSET_BITS=64...
	export ac_cv_sizeof_off_t=8
	sed -i s:OPT_GNUTLS/bin:OPT_GNUTLS:g ${S}/configure.ac
}
