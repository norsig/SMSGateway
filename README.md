# SMS Gateway

SMS Gateway to verify accounts through phone numbers.

## How it works?

Define url where we listen for new sms

    private final static String URL_LISTENER = "ws://server-ip:8888/foo/bar";

JSON format:

    {
      "phone_number": "+549123456789",
      "verification_code": "ASD123",
      "message": "test message"
    }

## Dependences
- [nv-websocket-client](https://github.com/TakahikoKawasaki/nv-websocket-client): High-quality WebSocket client implementation in Java.

## License

    Copyright 2016 Bruno Sarverry

    You may copy, distribute and modify the software as long as you track
    changes/dates in source files. Any modifications to or software including
    (via compiler) GPL-licensed code must also be made available under the GPL
    along with build & install instructions.

## Organization Keniobyte
Open source for Security Forces

[www.keniobyte.com](https://www.keniobyte.com/)

[info@keniobyte.com](mailto:info@keniobyte.com)

Tucumán, Argentina
