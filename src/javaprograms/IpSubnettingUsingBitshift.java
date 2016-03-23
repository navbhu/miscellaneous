package javaprograms;

public class IpSubnettingUsingBitshift {

    private static void getIpDetails(String ipAddress, int subnetMask){

        int allBytes = 0xffffffff;
        int hostBits = 32 - subnetMask;
        int ip = getIntValueOfIp(ipAddress);
        int subnet = allBytes << hostBits;
        int network = ip & subnet;
        int hostMin = network+1;
        int wildcard = allBytes >>> subnetMask;
        int broadcast = network + wildcard;
        int hostMax = broadcast-1;

        System.out.println("Ip Address : " + getIpFromInteger(ip));
        System.out.println("SubnetMask : " + getIpFromInteger(subnet));
        System.out.println("Network : " + getIpFromInteger(network));
        System.out.println("Broascast IP : "+ getIpFromInteger(broadcast));
        System.out.println("Host Min : "+ getIpFromInteger(hostMin));
        System.out.println("Host Max : "+ getIpFromInteger(hostMax));
        System.out.println("No of Hosts : " + Math.round(Math.pow(2,hostBits)-2));
    }

    private static String getIpFromInteger(int integerValueOfIP){
        int firstByte = 0x000000ff;
        int secondByte = 0x0000ff00;
        int thirdByte = 0x00ff0000;
        int fourthBytes = 0xff000000;
        return ((integerValueOfIP & fourthBytes) >>> 24) + "." + ((integerValueOfIP & thirdByte) >>> 16)
                + "." +((integerValueOfIP & secondByte) >>> 8) + "." +(integerValueOfIP & firstByte);
    }

    private static int getIntValueOfIp(String ipAddress){
        String[] octets = ipAddress.split("\\.");
        return (Integer.parseInt(octets[0]) << 24)+(Integer.parseInt(octets[1]) << 16)+(Integer.parseInt(octets[2]) << 8)+Integer.parseInt(octets[3]);
    }

    public static void main(String[] args){
        String[] input = args[0].split("/"); // "192.168.225.101/22".split("/");  // Input should be of the format 192.168.225.101/22
        getIpDetails(input[0], Integer.parseInt(input[1]));
    }

}
