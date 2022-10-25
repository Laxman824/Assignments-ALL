// g++ -std=c++17 vec.cpp && ./a.out 
// 3
/* Memory layout
 *                                   ┌───┐
 *                             ┌────►│[0]│
 *   ┌──────────────────────┐  │     ├───┤
 *   │                      │  │     │[1]│
 *   │   elem               ├──┼─┐   └───┘
 *   ├──────────────────────┤  │ │
 *v1 │   ptr                ├──┼─┼─►┌─────┐
 *   │                      ├──┘ └─►│ [0] │
 *   │   length             │    ┌─►└─────┘
 *   │   capacity           │    │
 *   ├──────────────────────┤    │
 *   │                      │    │
 *v0 │   ptr                ├────┘
 *   │   length             │
 *   │   capacity           │
 *   └──────────────────────┘
 *         stack                   heap
*/

#include <iostream>
#include <vector>

using namespace std;

int main() {
	vector<int> v0;
	vector<int> v1;

	v1.push_back(1);

	auto& elem = v1[0];

	v1.push_back(2);
	v0.push_back(3);

	cout << elem << "\n";
	return 0;
}