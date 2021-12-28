/*
 * Http GET Switch
 *
 * Calls URIs with HTTP GET for switch on or off
 *  also tracks state so if its on off state tracks
 *   the Toggle will assist in resetting mis tracked device states
 */
metadata {
    definition(name: "Http GET Toggle Switch", namespace: "community", author: "Community/pjf", importUrl: "https://github.com/pjf02536-2/HubitatPublic/blob/master/examples/drivers/httpGetSwitch.groovy") {
        capability "Actuator"
        capability "Switch"
        capability "Sensor"
        command "Toggle"
    }
}

preferences {
    section("URIs") {
        input "BaseURI", "text", title: "Base URI", required: false
        input "URIcmd", "text", title: "URI Command", required: false
        input name: "logEnable", type: "bool", title: "Enable debug logging", defaultValue: true
    }
}

def logsOff() {
    log.warn "debug logging disabled..."
    device.updateSetting("logEnable", [value: "false", type: "bool"])
}

def updated() {
    log.info "updated..."
    log.warn "debug logging is: ${logEnable == true}"
    if (logEnable) runIn(1800, logsOff)
}

def parse(String description) {
    if (logEnable) log.debug(description)
}

def on() {
    FullURI = settings.BaseURI + "/" + settings.URIcmd
    if (logEnable) log.debug "Sending on GET request to ${FullURI} or [${settings.BaseURI}${settings.URIcmd}]"

    try {
        if ( !state.IsOn ) {
            httpGet( FullURI ) { resp ->
                if (resp.success) {
                    state.IsOn = true
                    sendEvent(name: "switch", value: "on", isStateChange: true)
                }
            }
            if (logEnable)
                if (resp.data) log.debug "${resp.data}"
            }
        
    } catch (Exception e) {
        log.warn "Call to on failed: ${e.message}"
    }
}

def off() {
    FullURI = settings.BaseURI + "/" + settings.URIcmd
    if (logEnable) log.debug "Sending off GET request to [${settings.BaseURI}${settings.URIcmd}]"

    try {
        if ( state.IsOn ) {
            httpGet(FullURI) { resp ->
                if (resp.success) {
                    state.IsOn = false
                    sendEvent(name: "switch", value: "off", isStateChange: true)
                }
            }
            if (logEnable)
                if (resp.data) log.debug "${resp.data}"
        }
    } catch (Exception e) {
        log.warn "Call to off failed: ${e.message}"
    }
}


def Toggle() {
    FullURI = settings.BaseURI + "/" + settings.URIcmd
    if (logEnable) log.debug "Sending off GET request to [${FullURI}]"

    try {
            httpGet(FullURI) { resp ->
                 if (resp.success) {
                    state.IsOn = false
                    sendEvent(name: "switch", value: "off", isStateChange: true)
            }
            
            if (logEnable)
                if (resp.data) log.debug "${resp.data}"
        }
    } catch (Exception e) {
        log.warn "Call to off failed: ${e.message}"
    }
}
