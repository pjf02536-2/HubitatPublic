/*Broadlinkgo Generic integration
 * based on Hubitat example driver Http GET Switch
 *
 * Calls URIs and appends command for Broadlinkgo servers
 *  All commands are optional
 *
 * 2021-12-28 - Initial release from modified "http GET switch" 
 *               https://raw.githubusercontent.com/hubitat/HubitatPublic/master/examples/drivers/httpGetSwitch.groovy
 * 2021-12-30 - fixups and typo corrections
 * 2021-12-30 - Added off support for devices that dont toggle (only toggles if the OFF command is undefined , otherwise the off is issued )
 *            - more of the same, state set added
 *
 *
 */

static String version() {
	return "1.0.5c"
}

metadata {
    definition(name: "Broadlinkgo Generic Driver", namespace: "community", author: "Community/pjf", importUrl: "https://github.com/pjf02536-2/HubitatPublic/blob/master/examples/drivers/Broadlinkgo-Generic.groovy") {
        capability "Actuator"
        capability "Switch"
        capability "Sensor"
        command "PwrToggle"
        command "on"
        command "off"
        command "OK"
        command "Up"
        command "Down"
        command "Left"
        command "Right"
        command "n0"
        command "n1"
        command "n2"
        command "n3"
        command "n4"
        command "n5"
        command "n6"
        command "n7"
        command "n8"
        command "n9"
        command "n0"
        command "VolUp"
        command "VolDn"
        command "Mute"
        command "Eject"
        command "Menu"
        command "Home"
    }
}

preferences {
    section("URIs") {
        input "BaseURI", "text", title: "Base URI", required: false
        input "ONOFFcmd", "text", title: "ON Off URI Command", required: false
        input "OFFcmd"  , "text", title: "Off URI Command (if not toggle)", required: false
        input "UPcmd", "text", title: "Up Arrow Command", required: false
        input "DNcmd", "text", title: "Down Arrow Command", required: false
        input "RTcmd", "text", title: "Right Arrow Command", required: false
        input "LTcmd", "text", title: "Left Arrow Command", required: false
        input "OKcmd", "text", title: "OK Command", required: false
        
        input "D0cmd", "text", title: "Number 0 Command", required: false
        input "D1cmd", "text", title: "Number 1 Command", required: false
        input "D2cmd", "text", title: "Number 2 Command", required: false
        input "D3cmd", "text", title: "Number 3 Command", required: false
        input "D4cmd", "text", title: "Number 4 Command", required: false
        input "D5cmd", "text", title: "Number 5 Command", required: false
        input "D6cmd", "text", title: "Number 6 Command", required: false
        input "D7cmd", "text", title: "Number 7 Command", required: false
        input "D8cmd", "text", title: "Number 8 Command", required: false
        input "D9cmd", "text", title: "Number 9 Command", required: false
        
        input "VolUPcmd", "text", title: "Volume up Command", required: false
        input "VolDNcmd", "text", title: "Volume down Command", required: false
        input "Mutecmd", "text", title: "Mute Command", required: false
        
        input "EJcmd", "text", title: "Eject Command", required: false
        input "Playcmd", "text", title: "Play Command", required: false
        input "Stopcmd", "text", title: "Stop Command", required: false
        input "Menucmd", "text", title: "Menu Command", required: false
        input "Homecmd", "text", title: "Home Command", required: false
        
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


// ******************************************************************* //
def OK(){
    FullURI = settings.BaseURI + "/" + settings.OKcmd
    DoCommand ( FullURI )
    sendEvent(name: "Action", value: "OK", isStateChange: true)
}
// ******************************************************************* //
def Up(){
    FullURI = settings.BaseURI + "/" + settings.UPcmd
    IsSuccess = DoCommand ( FullURI )
    sendEvent(name: "Action", value: "Up Arrow", isStateChange: true)
}
// ******************************************************************* //
def Down(){
    FullURI = settings.BaseURI + "/" + settings.DNcmd
    IsSuccess = DoCommand ( FullURI )
    sendEvent(name: "Action", value: "Dn Arrow", isStateChange: true)
}
// ******************************************************************* //
def Left(){
    FullURI = settings.BaseURI + "/" + settings.LTcmd
    IsSuccess = DoCommand ( FullURI )
    sendEvent(name: "Action", value: "Left Arrow", isStateChange: true)
}
// ******************************************************************* //
def Right(){
    FullURI = settings.BaseURI + "/" + settings.RTcmd
    IsSuccess = DoCommand ( FullURI )
    sendEvent(name: "Action", value: "Right Arrow", isStateChange: true)
}


// ******************************************************************* //
def n0(){
    FullURI = settings.BaseURI + "/" + settings.D0cmd
    IsSuccess = DoCommand ( FullURI )
    state.digits = "0"
    sendEvent(name: "numbers", value: state.digits, isStateChange: true)
}
// ******************************************************************* //
def n1(){
    FullURI = settings.BaseURI + "/" + settings.D1cmd
    IsSuccess = DoCommand ( FullURI )
    state.digits =  "1"
    sendEvent(name: "numbers", value: state.digits, isStateChange: true)
}
// ******************************************************************* //
def n2(){
    FullURI = settings.BaseURI + "/" + settings.D2cmd
    IsSuccess = DoCommand ( FullURI )
    state.digits =  "2"
    sendEvent(name: "numbers", value: state.digits, isStateChange: true)
}
// ******************************************************************* //
def n3(){
    FullURI = settings.BaseURI + "/" + settings.D3cmd
    IsSuccess = DoCommand ( FullURI )
    state.digits =  "3"
    sendEvent(name: "numbers", value: state.digits, isStateChange: true)
}
// ******************************************************************* //
def n4(){
    FullURI = settings.BaseURI + "/" + settings.D4cmd
    IsSuccess = DoCommand ( FullURI )
    state.digits =  "4"
    sendEvent(name: "numbers", value: state.digits, isStateChange: true)
}
// ******************************************************************* //
def n5(){
    FullURI = settings.BaseURI + "/" + settings.D5cmd
    IsSuccess = DoCommand ( FullURI )
    state.digits =  "5"
    sendEvent(name: "numbers", value: state.digits, isStateChange: true)
}
// ******************************************************************* //
def n6(){
    FullURI = settings.BaseURI + "/" + settings.D6cmd
    IsSuccess = DoCommand ( FullURI )
    state.digits =  "6"
    sendEvent(name: "numbers", value: state.digits, isStateChange: true)
}
// ******************************************************************* //
def n7(){
    FullURI = settings.BaseURI + "/" + settings.D7cmd
    IsSuccess = DoCommand ( FullURI )
    state.digits =  "7"
    sendEvent(name: "numbers", value: state.digits, isStateChange: true)
}
// ******************************************************************* //
def n8(){
    FullURI = settings.BaseURI + "/" + settings.D8cmd
    IsSuccess = DoCommand ( FullURI )
    state.digits =  "8"
    sendEvent(name: "numbers", value: state.digits, isStateChange: true)
}
// ******************************************************************* //
def n9(){
    FullURI = settings.BaseURI + "/" + settings.D9cmd
    IsSuccess = DoCommand ( FullURI )
    state.digits =  "9"
    sendEvent(name: "numbers", value: state.digits, isStateChange: true)
}


// ******************************************************************* //
def VolUp(){
    FullURI = settings.BaseURI + "/" + settings.VolUPcmd
    IsSuccess = DoCommand ( FullURI )
    sendEvent(name: "Action", value: "Vol +", isStateChange: true)
}
// ******************************************************************* //
def VolDn(){
    FullURI = settings.BaseURI + "/" + settings.VolDNcmd
    IsSuccess = DoCommand ( FullURI )
    sendEvent(name: "Action", value: "Vol -", isStateChange: true)
    
}
// ******************************************************************* //
def Mute(){
    FullURI = settings.BaseURI + "/" + settings.Mutecmd
    IsSuccess = DoCommand ( FullURI )
    state.IsMute=true
    sendEvent(name: "mute", value: "yes", isStateChange: true)

}
// ******************************************************************* //
def Eject(){
    FullURI = settings.BaseURI + "/" + settings.EJcmd
    sendEvent(name: "Action", value: "eject", isStateChange: true)
    DoCommand ( FullURI )
}
// ******************************************************************* //
def Menu(){
    FullURI = settings.BaseURI + "/" + settings.Menucmd
    IsSuccess = DoCommand ( FullURI )
    sendEvent(name: "Action", value: "menu", isStateChange: true)
}

// ******************************************************************* //
def Home(){
    FullURI = settings.BaseURI + "/" + settings.Homecmd
    IsSuccess = DoCommand ( FullURI )
    sendEvent(name: "Action", value: "menu", isStateChange: true)
}

// ******************************************************************* //
def on() {
    // on is on reguardless of wether its a toggle or not
    FullURI = settings.BaseURI + "/" + settings.ONOFFcmd

    state.cmd = "on"

    if ( state.IsOn == false && settings.OFFcmd == null ) {
        state.IsOn = true
        sendEvent(name: "Power", value: "on", isStateChange: true)
        IsSuccess = DoCommand ( FullURI )
    } 

    if ( settings.OFFcmd != null){
        state.IsOn = true
        sendEvent(name: "Power", value: "on", isStateChange: true)
        IsSuccess = DoCommand ( FullURI )
    }
}
// ******************************************************************* //
def off() {
    // either toggle on off or just off if off command defined
    if ( state.IsOn == true && settings.OFFcmd == null) {
        FullURI = settings.BaseURI + "/" + settings.ONOFFcmd
        sendEvent(name: "Power", value: "OFF", isStateChange: true)
        IsSuccess = Call(DoCommand( FullURI ))
    }

    if ( settings.OFFcmd != null){
        FullURI = settings.BaseURI + "/" + settings.OFFcmd
        state.IsOn = false 
        sendEvent(name: "Power", value: "OFF", isStateChange: true)
        IsSuccess = Call(DoCommand( FullURI ))
    }

}
 
// ******************************************************************* //
def PwrToggle() {
    // Only if we dont define the off command
    if ( settings.OFFcmd != null ) {
        FullURI = settings.BaseURI + "/" + settings.ONOFFcmd
        state.IsOn = false
        sendEvent(name: "Power", value: "toggle", isStateChange: true)
        IsSuccess = DoCommand ( FullURI )
    }
}
// ******************************************************************* //
def DoCommand(FullURI){
    if (logEnable) log.debug "Sending off GET request to [${FullURI}]"

    try {
            httpGet(FullURI) { resp ->
            if (logEnable)
                if (resp.data) log.debug "${resp.data}"
            }
    } catch (Exception e) {
        log.warn "Call to off failed: ${e.message}"
    }
    
   //state.IsSuccess = resp.success
    // return
    state.IsSuccess = resp.success
    return state.IsSuccess
}
