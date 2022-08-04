#include <iostream>
using namespace std;
int index =0;
int n;

void fillArray(int a[],int max, int min){
    if (index >= n)
        return;
    if (max == min){
        a[index++] = max;
        return;
    }
    fillArray(a,max,min + 1);
    if (index >= n)
        return;
    a[index++] = min;
    fillArray(a,max,min + 1);
}

int main() {
    int k;
    cin>>n>>k;
    int a[n];
    fillArray(a,k,1);
    if (index >= n) {
        for (int i = 0; i < n; i++) {
            cout<<a[i] << " ";
        }
    } else {
        cout<<"Impossible";
    }
    return 0;
}