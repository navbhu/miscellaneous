package javaprograms;


//This program will print subnet mask, broadcast ip, network ip, min and max host ip of the input network.
public class ListIPRangeInSubnet {

    private static final int OCTET_SIZE = 32;

    //Convert IP to byte
    private static byte[] ipToByte(String ip){
        String[] octets = ip.split("\\.");
        byte[] byteValue = new byte[OCTET_SIZE];
        int bitPosition = 31;
        int octetPosition = 0;
        while(octetPosition<4) {
            int octetValue = Integer.parseInt(octets[octetPosition]);
            int quotient = 0;
            int divisor = 128;
            while (divisor > 0) {
                quotient = octetValue / divisor;
                int reminder = octetValue % divisor;
                octetValue = reminder;
                byteValue[bitPosition] = (byte) quotient;
                bitPosition--;
                divisor = divisor / 2;
            }
            octetPosition++;
        }
        return byteValue;
    }

    //Convert bytes to IP.
    private static String byteToIp(byte[] bytes){
        String ip = "";
        if(bytes.length <=OCTET_SIZE){
            int bitPosition = 31;
            while(bitPosition>=0) {
                int multiplier = 128;
                int integerVal = 0;
                while (multiplier > 0) {
                    integerVal = integerVal + bytes[bitPosition]*multiplier;
                    multiplier = multiplier/2;
                    bitPosition--;
                }
                ip = ip+integerVal+".";
            }
        }
        return ip;
    }

    public static void main(String[] args){
        String[] input = args[0].split("/"); //Input should be of the format 192.20.10.5/22
        String ip = input[0];
        int subnetSize = Integer.parseInt(input[1]);
        int networkSize = OCTET_SIZE - subnetSize;
        byte[] ipInByte = ListIPRangeInSubnet.ipToByte(ip);
        byte[] subnetMask = new byte[OCTET_SIZE];
        byte[] subnet = new byte[OCTET_SIZE];
        byte[] broadcast = new byte[OCTET_SIZE];
        byte[] hostMin = new byte[OCTET_SIZE];
        byte[] hostMax = new byte[OCTET_SIZE];

        for(int bitPosition=31; bitPosition >= networkSize; bitPosition--){
            subnetMask[bitPosition]=1;
        }

        for(int bitPosition=31; bitPosition>=0; bitPosition--){
            byte b = (byte)(ipInByte[bitPosition]&subnetMask[bitPosition]);
            subnet[bitPosition]=b;
            broadcast[bitPosition] = b;
            hostMin[bitPosition] = b;
            hostMax[bitPosition] = b;
        }

        for(int bitPosition=networkSize-1; bitPosition>=0; bitPosition--){
            broadcast[bitPosition]=1;
            hostMax[bitPosition] = 1;
        }

        hostMin[0]=1;
        hostMax[0]=0;
        System.out.println("IP Address : " + byteToIp(ipInByte));
        System.out.println("SubnetMask : " + byteToIp(subnetMask));
        System.out.println("Network : " + byteToIp(subnet));
        System.out.println("Broadcast IP : " + byteToIp(broadcast));
        System.out.println("Host Min : " + byteToIp(hostMin));
        System.out.println("Host Max : " + byteToIp(hostMax));
    }
}
