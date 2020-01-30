
package com.tinkerforge;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.time.ZonedDateTime;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.Arrays;
import java.util.List;
import java.net.URI;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.function.Function;
import com.tinkerforge.Device.SetterRefresh;

import java.util.function.BiConsumer;
import org.eclipse.smarthome.config.core.Configuration;
import org.eclipse.smarthome.config.core.ConfigDescription;
import org.eclipse.smarthome.config.core.ConfigDescriptionBuilder;
import org.eclipse.smarthome.config.core.ConfigDescriptionParameter.Type;
import org.eclipse.smarthome.config.core.ConfigDescriptionParameterBuilder;
import org.eclipse.smarthome.config.core.ConfigDescriptionParameterGroup;
import org.eclipse.smarthome.config.core.ParameterOption;
import org.eclipse.smarthome.core.types.State;
import org.eclipse.smarthome.core.types.StateOption;
import org.eclipse.smarthome.core.types.Command;
import org.eclipse.smarthome.core.types.CommandDescriptionBuilder;
import org.eclipse.smarthome.core.types.CommandOption;
import org.eclipse.smarthome.core.thing.ThingTypeUID;
import org.eclipse.smarthome.core.thing.type.ChannelDefinitionBuilder;
import org.eclipse.smarthome.core.thing.type.ChannelType;
import org.eclipse.smarthome.core.thing.type.ChannelTypeBuilder;
import org.eclipse.smarthome.core.thing.type.ChannelTypeUID;
import org.eclipse.smarthome.core.thing.type.ThingType;
import org.eclipse.smarthome.core.thing.type.ThingTypeBuilder;
import org.eclipse.smarthome.core.types.StateDescriptionFragmentBuilder;
import org.eclipse.smarthome.binding.tinkerforge.internal.TinkerforgeBindingConstants;
import org.eclipse.smarthome.binding.tinkerforge.internal.handler.BrickletRemoteSwitchHandler;
import org.eclipse.smarthome.binding.tinkerforge.internal.handler.BrickletRemoteSwitchHandler.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.eclipse.smarthome.core.library.types.DateTimeType;
import org.eclipse.smarthome.core.library.types.OnOffType;
import org.eclipse.smarthome.core.library.types.QuantityType;
import org.eclipse.smarthome.core.library.types.StringType;
import org.eclipse.smarthome.core.library.unit.MetricPrefix;
import org.eclipse.smarthome.core.library.unit.SIUnits;
import org.eclipse.smarthome.core.library.unit.SmartHomeUnits;


public class RemoteDimmerTypeB {
    public RemoteDimmerTypeB(BrickletRemoteSwitchHandler handler) {
        this.handler = handler;
    }

    private final BrickletRemoteSwitchHandler handler;

    public final static int DEVICE_IDENTIFIER = -235;
    public final static String DEVICE_DISPLAY_NAME = "Remote Dimmer Type B";

    public final static DeviceInfo DEVICE_INFO = new DeviceInfo(DEVICE_DISPLAY_NAME, "remotedimmertypeb",
            DEVICE_IDENTIFIER, RemoteDimmerTypeB.class, DefaultActions.class, "1.0.0");

    private final Logger logger = LoggerFactory.getLogger(BrickletRemoteSwitch.class);
    private final static Logger static_logger = LoggerFactory.getLogger(BrickletRemoteSwitch.class);

    public void initialize(org.eclipse.smarthome.config.core.Configuration config, Function<String, org.eclipse.smarthome.config.core.Configuration> getChannelConfigFn, BiConsumer<String, org.eclipse.smarthome.core.types.State> updateStateFn, BiConsumer<String, String> triggerChannelFn) {
        RemoteDimmerTypeBConfig cfg = (RemoteDimmerTypeBConfig) config.as(RemoteDimmerTypeBConfig.class);
    }

    public List<String> getEnabledChannels(org.eclipse.smarthome.config.core.Configuration config)
            throws TinkerforgeException {
        return Arrays.asList("RemoteDimmerTypeBValue");
    }

    public static ChannelType getChannelType(ChannelTypeUID channelTypeUID) {
        switch (channelTypeUID.getId()) {
        case "RemoteDimmerTypeBValue":
            return ChannelTypeBuilder
                    .state(new ChannelTypeUID("tinkerforge", "RemoteDimmerTypeBValue"), "Value",
                            "Number:Dimensionless")
                    .withConfigDescriptionURI(URI.create("channel-type:tinkerforge:RemoteDimmerTypeBValue"))
                    .withDescription("Dim value between 0 and 15. -1 switches the dimmer off.")
                    .withStateDescription(StateDescriptionFragmentBuilder.create().withMinimum(BigDecimal.valueOf(-1)).withMaximum(BigDecimal.valueOf(15)).withStep(BigDecimal.valueOf(1)).build().toStateDescription())
                    .build();
        default:
            static_logger.debug("Unknown channel type ID {}", channelTypeUID.getId());
            break;
        }

        return null;
    }

    public static ThingType getThingType(ThingTypeUID thingTypeUID) {
        return ThingTypeBuilder.instance(thingTypeUID, "Tinkerforge Remote Dimmer Type B").isListed(true)
                .withSupportedBridgeTypeUIDs(Arrays.asList(TinkerforgeBindingConstants.THING_TYPE_BRICKLET_REMOTE_SWITCH.toString(),
                                                           TinkerforgeBindingConstants.THING_TYPE_BRICKLET_REMOTE_SWITCH_V2.toString()))
                .withConfigDescriptionURI(URI.create("thing-type:tinkerforge:" + thingTypeUID.getId()))
                .withDescription("Remote controlled mains dimmer (Type B) for Remote Switch Bricklet or Remote Switch Bricklet 2.0")
                .withChannelDefinitions(Arrays.asList(
                        new ChannelDefinitionBuilder("RemoteDimmerTypeBValue",
                                new ChannelTypeUID("tinkerforge", "RemoteDimmerTypeBValue")).withLabel("Dim Value").build()))
                .build();
    }

    public static ConfigDescription getConfigDescription(URI uri) {
        switch (uri.toASCIIString()) {
        case "thing-type:tinkerforge:remotedimmertypeb":
            return ConfigDescriptionBuilder
                    .create(uri)
                    .withParameters(
                        Arrays.asList(
                            ConfigDescriptionParameterBuilder
                                .create("address", Type.INTEGER)
                                .withDefault("0")
                                .withDescription("The address of the remote dimmer to control.")
                                .withMinimum(BigDecimal.valueOf(0))
                                .withMaximum(BigDecimal.valueOf(67108863))
                                .build(),
                            ConfigDescriptionParameterBuilder
                                .create("unit", Type.INTEGER)
                                .withDefault("0")
                                .withDescription("The unit of the remote dimmer to control.")
                                .withMinimum(BigDecimal.valueOf(0))
                                .withMaximum(BigDecimal.valueOf(15))
                                .build(),
                            ConfigDescriptionParameterBuilder
                                .create("repeats", Type.INTEGER)
                                .withDefault("5")
                                .withDescription("Sets the number of times the code is sent when of the socket is toggled. The repeats basically correspond to the amount of time that a button of the remote is pressed.")
                                .withMinimum(BigDecimal.valueOf(0))
                                .withMaximum(BigDecimal.valueOf(255))
                                .build()))
                    .build();
        case "channel-type:tinkerforge:RemoteDimmerTypeBValue":
            return ConfigDescriptionBuilder.create(uri).build();
        default:
            static_logger.debug("Unknown config description URI {}", uri.toASCIIString());
            break;
        }
        return null;
    }


    public void refreshValue(String value, org.eclipse.smarthome.config.core.Configuration config, org.eclipse.smarthome.config.core.Configuration channelConfig, BiConsumer<String, org.eclipse.smarthome.core.types.State> updateStateFn, BiConsumer<String, String> triggerChannelFn) throws TinkerforgeException {
        RemoteDimmerTypeBConfig cfg = (RemoteDimmerTypeBConfig) config.as(RemoteDimmerTypeBConfig.class);
        switch(value) {
            case "RemoteDimmerTypeBValue":
                break;
            default:
                logger.warn("Refresh for unknown channel {}", value);
                break;
        }
    }

    public List<SetterRefresh> handleCommand(org.eclipse.smarthome.config.core.Configuration config, org.eclipse.smarthome.config.core.Configuration channelConfig, String channel, Command command) throws TinkerforgeException {
        List<SetterRefresh> result = Collections.emptyList();
        RemoteDimmerTypeBConfig cfg = (RemoteDimmerTypeBConfig) config.as(RemoteDimmerTypeBConfig.class);
        switch(channel) {
            case "RemoteDimmerTypeBValue":
                if (command instanceof Number) {
                    Number cmd = (Number) command;
                    int val = cmd.intValue();
                    if(val >= 0) {
                        handler.enqueue(new Task(
                            rs -> {
                                rs.setRepeats(cfg.repeats);
                                rs.dimSocketB(cfg.address, cfg.unit, cmd.intValue());
                            },
                            success -> logger.warn("Address {} Unit {} command {}", cfg.address, cfg.unit, cmd.intValue())));
                    } else {
                        handler.enqueue(new Task(
                            rs -> {
                                rs.setRepeats(cfg.repeats);
                                rs.switchSocketB(cfg.address, cfg.unit, 0);
                            },
                            success -> {
                                if(!success)
                                    logger.warn("Address {} Unit {} command {} failed", cfg.address, cfg.unit, "OFF");
                            }));
                    }
                }

                else {
                    logger.warn("Command type {} not supported for channel {}. Please use one of Number.", command.getClass().getName(), channel);
                }

                break;
            default:
                logger.warn("Command for unknown channel {}", channel);
        }
        return result;
    }
}