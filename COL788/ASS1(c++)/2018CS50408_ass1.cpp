#include<bits/stdc++.h>
using namespace std;

// Returns XOR of 'a' and 'b'
// (both of same length)
string xor1(string a, string b)
{
	
	// Initialize result
	string result = "";
	
	int n = b.length();
	
	// Traverse all bits, if bits are
	// same, then XOR is 0, else 1
	for(int i = 1; i < n; i++)
	{
		if (a[i] == b[i])
			result += "0";
		else
			result += "1";
	}
	return result;
}

// Performs Modulo-2 division
string mod2div(string divident, string divisor)
{
	
	// Number of bits to be XORed at a time.
	int pick = divisor.length();
	
	// Slicing the divident to appropriate
	// length for particular step
	string tmp = divident.substr(0, pick);
	
	int n = divident.length();
	
	while (pick < n)
	{
		if (tmp[0] == '1')
		
			// Replace the divident by the result
			// of XOR and pull 1 bit down
			tmp = xor1(divisor, tmp) + divident[pick];
		else
		
			// If leftmost bit is '0'.
			// If the leftmost bit of the dividend (or the
			// part used in each step) is 0, the step cannot
			// use the regular divisor; we need to use an
			// all-0s divisor.
			tmp = xor1(std::string(pick, '0'), tmp) +
				divident[pick];
				
		// Increment pick to move further
		pick += 1;
	}
	
	// For the last n bits, we have to carry it out
	// normally as increased value of pick will cause
	// Index Out of Bounds.
	if (tmp[0] == '1')
		tmp = xor1(divisor, tmp);
	else
		tmp = xor1(std::string(pick, '0'), tmp);
		
	return tmp;
}

// Function used at the sender side to encode
// data by appending remainder of modular division
// at the end of data.
string encodeData(string data, string key)
{
	int l_key = key.length();
	
	// Appends n-1 zeroes at end of data
	string appended_data = (data +
							std::string(
								l_key - 1, '0'));
	
	string remainder = mod2div(appended_data, key);
	
	// Append remainder in the original data
	cout<<"The remainder of the message(CRC) is:"<< remainder<<endl;
	string codeword = data + remainder;
	cout<<"The  dividend of the message is :"<<codeword<<endl;
	return codeword;
}
bool check(string data, string key){
	string remainder=mod2div(data,key);
	if(remainder=="0000000000000000"){return true;}
	return false;
}

// Driver code
int main()
{	cout<<"Please enter a message\n";
	string temp;
	cin>>temp;
	int k=temp.length();
	string dividend = "";
	
	for (int i = 0; i < k; i++)
    {
        // convert each char to
        // ASCII value
        int val = int(temp[i]);
        
        // Convert ASCII value to binary
       dividend.push_back('0');
       while(val>0)
{            (val % 2)? dividend.push_back('1') :
                       dividend.push_back('0');
            val /= 2;
        
	}   }
 
  string divisor="10001000000100001";
  string res=encodeData(dividend,divisor);
  string inp ;
  cout<<"Please enter the recieved message  from the above :";
  cin>>inp;
  bool z=check(inp,divisor);
        
    if(z==1){cout<<"There is no error while transmission of message \n";}
	else{cout<<"There is an error!!!!!!!!!!!!!!! \n";}
	
	return 0;
}
