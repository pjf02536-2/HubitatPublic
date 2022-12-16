/*Broadlinkgo Generic integration
 * based on Hubitat example driver Http GET Switch
 *
 * Calls URIs and appends command for Broadlinkgo servers
 *  Requires Brioadlinkgo service running and configured locally
 *  All commands are optional
 *
 * 2021-12-28 - Initial release from modified "http GET switch" 
 *               https://raw.githubusercontent.com/hubitat/HubitatPublic/master/examples/drivers/httpGetSwitch.groovy
 * 2021-12-30 - fixups and typo corrections
 * 2021-12-30 - Added off support for devices that dont toggle (only toggles if the OFF command is undefined , otherwise the off is issued )
 *            - more of the same, state set added
 *
 * 2022-01-05 - fixups and some added debugging if on.  Added setting for difficult things to Play command twice (eg cheap LED string controllers )
 * 2022-01-11 - Updates for Color controls (just setColor for now).  Mapped color hues to colors. Need to add comments for commands to color nx()
 * 2022-12-16 - Update to Toggle power and Toggle settings in power on off (was not toggling if requested) and on off was iffy
 */

static String version() {
	return "1.2.0"
}

metadata {
    definition(name: "Broadlinkgo Generic Driver", namespace: "community", author: "Community/pjf", importUrl: "https://github.com/pjf02536-2/HubitatPublic/blob/master/examples/drivers/Broadlinkgo-Generic.groovy") {
        capability "Actuator"
        capability "Switch"
    	capability "Refresh"  
        capability "Sensor"
        capability "Light"
        capability "ColorControl"
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
        
        input "D0cmd", "text", title: "Number 0 Command (white)", required: false
        input "D1cmd", "text", title: "Number 1 Command (red)", required: false
        input "D2cmd", "text", title: "Number 2 Command (green)", required: false
        input "D3cmd", "text", title: "Number 3 Command (blue)" , required: false
        input "D4cmd", "text", title: "Number 4 Command (yellow)", required: false
        input "D5cmd", "text", title: "Number 5 Command (blue-green)", required: false
        input "D6cmd", "text", title: "Number 6 Command (violet)", required: false
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
        input name: "RepeatCMD", type: "bool", title: "Issue Command Twice", defaltValue: false
    }
}

def setColor(colormap) {
    if (logEnable) log.debug "setColor ()Map SAT:${colormap.saturation} HUE:${colormap.hue} Level: ${colormap.level}"
    
    if (colormap.saturation < 60 && colormap.level > 90 && colormap.saturatoin != null){
//    if (colormap.level > 90 ){
            n0()
    } else {
        if (logEnable) log.debug "SWITCH Map SAT:${colormap.saturation} HUE:${colormap.hue} Level: ${colormap.level}"

        //google sux and returns Text not integers
        Hueue = colormap.hue;
        Hue = Hueue.toInteger()
        
        switch ( Hue ) {
            //orange
            case 0..10:
                if (logEnable) log.debug "SWITCH-Case Orange Map SAT:${colormap.saturation} HUE:${colormap.hue} Level: ${colormap.level}"
                n1()
                break
            //yellow
            case 11..19:
                if (logEnable) log.debug "SWITCH-Case yellow Map SAT:${colormap.saturation} HUE:${colormap.hue} Level: ${colormap.level}"
                n4()
                break
            //green
            case 20..34:
                if (logEnable) log.debug "SWITCH-Case green Map SAT:${colormap.saturation} HUE:${colormap.hue} Level: ${colormap.level}"
                n2()
                break
            //blue-breen
            case 35..42:
                if (logEnable) log.debug "SWITCH-Case BReen Map SAT:${colormap.saturation} HUE:${colormap.hue} Level: ${colormap.level}"
                n5()
                break
            //Blue
            case 43..69:
                if (logEnable) log.debug "SWITCH-Case Map Blue SAT:${colormap.saturation} HUE:${colormap.hue} Level: ${colormap.level}"
                n3()
                break
            // Voilet
            case 70..89:
                if (logEnable) log.debug "SWITCH-Case Voilet Map SAT:${colormap.saturation} HUE:${colormap.hue} Level: ${colormap.level}"
                n6()
                break
            //Red
            case 90..100:
                if (logEnable) log.debug "SWITCH-Case Rough Map SAT:${colormap.saturation} HUE:${colormap.hue} Level: ${colormap.level}"
                n1()
                break
        
        }
        if (logEnable) log.debug "SWITCH-Out Map SAT:${colormap.saturation} HUE:${colormap.hue} Level: ${colormap.level}"
    }
}
def setHue(hue) {
    if (logEnable) log.debug "Hue is set to ${hue}"
    setColor([hue: hue]) 
    if ( hue == 100){
        //n1()
    }
}

def setSaturation(saturation) {
        if (logEnable) log.debug "Sat is set to ${saturation}"
        
}

/* Standard Stuff here */
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

    log.debug "ON states ${FullURI}"
    state.cmd = "on"

    if ( state.IsOn == false && settings.OFFcmd == null ) {
//        state.IsOn = true
        IsSuccess = DoCommand ( FullURI )
        if (logEnable) log.debug "ON CMD Toggle Complete"

    } 

    if ( settings.OFFcmd != null){
//        state.IsOn = true
        IsSuccess = DoCommand ( FullURI )
        if (logEnable) log.debug "ON CMD ON Cpmplete"

    }

    state.IsOn = true
    sendEvent(name: "Power", value: "on", isStateChange: true)
    sendEvent(name: "success", value: IsSuccess, isStateChange: true)
//    sendEvent(name: "success", value: " ON: ${IsSuccess}", isStateChange: true)
    
}
// ******************************************************************* //
def off() {
    // either toggle on off or just off if off command defined
    // Toggle OFF
    
    if ( state.IsOn == true && settings.OFFcmd == null) {
        FullURI = settings.BaseURI + "/" + settings.ONOFFcmd
        IsSuccess = DoCommand( FullURI )
        if (logEnable) log.debug "OFF CMD Toggle Completed"
    }

    // SET OFF
    if ( settings.OFFcmd != null){
        FullURI = settings.BaseURI + "/" + settings.OFFcmd
        IsSuccess = DoCommand( FullURI )
        if (logEnable) log.debug "OFF CMD OFF Completed"
        
    }

    state.IsOn = false 
    sendEvent(name: "Power", value: "OFF", isStateChange: true)
    sendEvent(name: "success", value: " off: ${IsSuccess}", isStateChange: true)

}
 
// ******************************************************************* //
def PwrToggle() {
    // Only if we dont define the off command
    if ( settings.OFFcmd == null ) {
        FullURI = settings.BaseURI + "/" + settings.ONOFFcmd
        state.IsOn = false
        sendEvent(name: "Power", value: "toggle", isStateChange: true)
        IsSuccess = DoCommand( FullURI )
    }
}
// ******************************************************************* //
def DoCommand(FullURI){
    if (logEnable) log.debug "Sending GET request : [${FullURI}]"

    try {
            httpGet(FullURI) { resp ->
                if (resp.success) {
                    IsSuccess = true
                } else {
                    IsSuccess = false
                }
                
                //Repeat if set
                if ( RepeatCMD == true ){
                    httpGet(FullURI) { answ ->
                    if (logEnable) log.debug "DoCommand Sent TWice: Success"
                    }
                }
                

                if (logEnable)
                    if (resp.data) log.debug "${resp.data}"
                }
        } catch (Exception e) {
            log.warn "Call to off failed: ${e.message}"
        }
    if (logEnable) log.debug "DoCommand Sent : Success"

   return 
}
