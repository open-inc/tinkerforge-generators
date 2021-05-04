function octave_example_enumerate()
    more off;

    HOST = "localhost";
    PORT = 4223;

    ipcon = javaObject("com.tinkerforge.IPConnection"); % Create IP connection

    ipcon.connect(HOST, PORT); % Connect to brickd

    % Register Enumerate Callback
    ipcon.addEnumerateCallback(@cb_enumerate);

    % Trigger Enumerate
    ipcon.enumerate();

    input("Press any key to exit...\n", "s");
    ipcon.disconnect();
end

% Print incoming enumeration
function cb_enumerate(e)
    ipcon = e.getSource();

    fprintf("UID: %s\n", e.uid);
    fprintf("Enumeration Type: %d\n", java2int(e.enumerationType));

    if java2int(e.enumerationType) == java2int(ipcon.ENUMERATION_TYPE_DISCONNECTED)
        fprintf("\n");
        return;
    end

    hardwareVersion = e.hardwareVersion;
    firmwareVersion = e.firmwareVersion;

    fprintf("Connected UID: %s\n", e.connectedUid);
    fprintf("Position: %s\n", e.position);
    fprintf("Hardware Version: %d.%d.%d\n", java2int(hardwareVersion(1)), ...
                                            java2int(hardwareVersion(2)), ...
                                            java2int(hardwareVersion(3)));
    fprintf("Firmware Version: %d.%d.%d\n", java2int(firmwareVersion(1)), ...
                                            java2int(firmwareVersion(2)), ...
                                            java2int(firmwareVersion(3)));
    fprintf("Device Identifier: %d\n", e.deviceIdentifier);
    fprintf("\n");
end

function int = java2int(value)
    if compare_versions(version(), "3.8", "<=")
        int = value.intValue();
    else
        int = value;
    end
end
