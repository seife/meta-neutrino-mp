pkg_prerm_${PN} () {
#!/bin/sh
# do not stop networking on update or download of other packages will fail
exit 0
}

pkg_preinst_${PN} () {
#!/bin/sh
if type update-rc.d >/dev/null 2>/dev/null; then
        if [ -n "$D" ]; then
                OPT="-f -r $D"
        else
                OPT="-f"
        fi
        update-rc.d $OPT networking remove
fi
exit
# the original code would stop networking, which is a bad thing to do...
}
