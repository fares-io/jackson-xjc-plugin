# Jackson XJC Plugin

This plugin provides tooling to add Jackson annotations to generated Java Beans.

## Usage

1. Configure the XJC compiler

The plugin can be used with any JAXB compiler that is capable of registering XJC plugins. The plugin jar needs to be made available to the XJC compiler classpath. In maven this is not the project classpath but the classpath of the plugin that generates code from one or more XML schema.

Example configuration for the JAXB2 commons compiler:

```xml
<plugin>
  <groupId>org.jvnet.jaxb2.maven2</groupId>
  <artifactId>maven-jaxb2-plugin</artifactId>
  <configuration>
    <plugins>
      <plugin>
        <groupId>io.fares.bind.xjc.plugins</groupId>
        <artifactId>jackson-xjc-plugin</artifactId>
        <version>0.0.1</version>
      </plugin>
    </plugins>
    <extension>true</extension>
    <args>
      <arg>-Xjackson</arg>
    </args>
  </configuration>
</plugin>
```

2. You are ready to receive Jackson goodness

The plugin will now enhance the enum `VehicleType` with the Jackson annotations so that the values are serialised/deserialised as defined in the schema, NOT as dictated by Java convention.

```java
@XmlType(name = "VehicleType")
@XmlEnum
public enum VehicleType {

    @XmlEnumValue("Car")
    CAR("Car"),
    @XmlEnumValue("Boat")
    BOAT("Boat"),
    @XmlEnumValue("Plane")
    PLANE("Plane");
    private final String value;

    VehicleType(String v) {
        value = v;
    }

    @JsonValue
    public String value() {
        return value;
    }

    @JsonCreator
    public static VehicleType fromValue(String v) {
        for (VehicleType c: VehicleType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
```
