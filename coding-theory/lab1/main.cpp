#include <algorithm>
#include <assert.h>
#include <cmath>
#include <fstream>
#include <iostream>
#include <random>
#include <set>
#include <string>
#include <vector>

using namespace std;

namespace {

using LD = long double;

using Vector = vector<bool>;
using VectorD = vector<LD>;
using Matrix = vector<Vector>;

using Val = pair<int, int>;

struct Vertex {
  vector<Val> Symbols;

  int PrevOne = -1;
  int PrevZero = -1;

  int Id = -1;
  int Layer = -1;

  LD Distance = -1.0;
  bool FromZero = false;
};

using Layer = vector<Vertex>;
using Grid = vector<Layer>;

void addToVector(Vector &To, const Vector &From) {
  assert(To.size() == From.size());
  for (int Ind = 0; Ind < To.size(); Ind++) {
    To[Ind] = To[Ind] ^ From[Ind];
  }
}

void upToDown(Matrix &M) {
  int N = M[0].size();
  int K = M.size();
  int I = 0, J = 0;

  while (true) {
    if (J == N - 1 || I == K)
      break;
    if (!M[I][J]) {
      for (int It = I + 1; It < K; It++) {
        if (M[It][J]) {
          swap(M[I], M[It]);
          break;
        }
      }
      if (!M[I][J]) {
        J++;
        continue;
      }
    }
    assert(M[I][J]);
    for (int It = I + 1; It < K; It++) {
      if (M[It][J]) {
        addToVector(M[It], M[I]);
      }
    }
    J++;
    I++;
  }
}

void downToUp(Matrix &M) {
  int N = M[0].size();
  int K = M.size();
  int J = N - 1;

  set<int, greater<int>> Active;
  for (int It = 0; It < K; It++)
    Active.insert(It);

  while (Active.size() != 1) {
    int LowestRow = -1;
    for (int It : Active) {
      if (M[It][J]) {
        LowestRow = It;
        break;
      }
    }
    if (LowestRow == -1) {
      J--;
      continue;
    }
    assert(M[LowestRow][J]);
    Active.erase(LowestRow);
    for (int It : Active) {
      if (M[It][J]) {
        addToVector(M[It], M[LowestRow]);
      }
    }
    J--;
  }
}

Matrix toSpanForm(Matrix M) {
  upToDown(M);
  downToUp(M);
  return M;
}

vector<set<int>> findSpans(const Matrix &M) {
  vector<set<int>> Spans(M[0].size());
  for (int I = 0; I < M.size(); I++) {
    int L = 0;
    while (!M[I][L]) {
      L++;
    }
    int R = M[I].size() - 1;
    while (!M[I][R]) {
      R--;
    }

    for (; L < R; L++) {
      Spans[L].insert(I);
    }
  }
  return Spans;
}

bool createEdge(const vector<Val> &Symbols, const Matrix &M, int J) {
  if (Symbols.size() == 1 && Symbols.back().first == -1) {
    return false;
  }
  bool Res = false;
  for (auto T : Symbols) {
    Res ^= ((T.second == 1) && M[T.first][J]);
  }
  return Res;
}

bool createEdge(const vector<Val> &Symbols, const Val &NewVal, const Matrix &M,
                int J) {
  auto Res = createEdge(Symbols, M, J);
  Res ^= ((NewVal.second == 1) && M[NewVal.first][J]);
  return Res;
}

static inline auto getLayerVertex(Layer &L, const vector<Val> &Symbols) {
  return find_if(L.begin(), L.end(),
                 [&](const Vertex &V) { return Symbols == V.Symbols; });
}

void addNewVertex(int LayerId, Layer &NewLayer, const Vertex &V,
                  const vector<Val> &Symbols, bool Edge) {
  auto It = getLayerVertex(NewLayer, Symbols);

  if (It == NewLayer.end()) {
    Vertex NewVertex;
    NewVertex.Symbols = Symbols;
    NewVertex.Id = distance(NewLayer.begin(), It);
    NewVertex.Layer = LayerId;
    NewLayer.push_back(NewVertex);
    if (Edge) {
      NewLayer.back().PrevOne = V.Id;
    } else {
      NewLayer.back().PrevZero = V.Id;
    }
  } else {
    if (Edge) {
      It->PrevOne = V.Id;
    } else {
      It->PrevZero = V.Id;
    }
  }
}

Grid buildGrid(const Matrix &M, const vector<set<int>> &Spans, int N) {
  Grid R;

  Layer FirstLayer(1);
  FirstLayer.back().Id = 0;
  FirstLayer.back().Layer = 0;
  R.push_back(FirstLayer);
  set<int> CurrSpans;
  for (int J = 0; J < N; J++) {
    Layer NewLayer;
    set<int> Common;
    set_intersection(CurrSpans.begin(), CurrSpans.end(), Spans[J].begin(),
                     Spans[J].end(), inserter(Common, Common.end()));

    set<int> NewValues;
    set_difference(Spans[J].begin(), Spans[J].end(), Common.begin(),
                   Common.end(), inserter(NewValues, NewValues.end()));

    CurrSpans = Spans[J];
    assert(NewValues.size() < 2 && "Spans are equals on build grid");
    for (int I = 0; I < R.back().size(); I++) {
      const auto &Vertex = R.back()[I];
      vector<Val> CommonValues;
      for (auto V : Vertex.Symbols) {
        if (Common.count(V.first)) {
          CommonValues.push_back(V);
        }
      }
      if (NewValues.size() > 0) {
        int NewValC = *NewValues.begin();

        auto First = CommonValues;
        Val NewVal = {NewValC, 0};
        First.push_back(NewVal);
        bool Edge = createEdge(Vertex.Symbols, NewVal, M, J);
        addNewVertex(J + 1, NewLayer, Vertex, First, Edge);

        auto Second = CommonValues;
        NewVal = {NewValC, 1};
        Second.push_back(NewVal);
        Edge = createEdge(Vertex.Symbols, NewVal, M, J);
        addNewVertex(J + 1, NewLayer, Vertex, Second, Edge);
      } else if (!CommonValues.empty()) {
        bool Edge = createEdge(Vertex.Symbols, M, J);
        addNewVertex(J + 1, NewLayer, Vertex, CommonValues, Edge);
      } else {
        bool Edge = createEdge(Vertex.Symbols, M, J);
        addNewVertex(J + 1, NewLayer, Vertex, {{-1, 0}}, Edge);
      }
    }
    R.push_back(NewLayer);
  }
  return R;
}

void setMinDist(Grid &G, Vertex &V, LD Sym) {
  bool HasPrevZero = V.PrevZero != -1;
  bool HasPrevOne = V.PrevOne != -1;
  if (HasPrevZero && HasPrevOne) {
    LD ZeroPath = G[V.Layer - 1][V.PrevZero].Distance + abs(1.0 - Sym);
    LD OnePath = G[V.Layer - 1][V.PrevOne].Distance + abs(-1.0 - Sym);
    if (ZeroPath < OnePath) {
      V.Distance = ZeroPath;
      V.FromZero = true;
    } else {
      V.Distance = OnePath;
      V.FromZero = false;
    }
  } else if (HasPrevOne) {
    LD OnePath = G[V.Layer - 1][V.PrevOne].Distance + abs(-1.0 - Sym);
    V.Distance = OnePath;
    V.FromZero = false;
  } else if (HasPrevZero) {
    LD ZeroPath = G[V.Layer - 1][V.PrevZero].Distance + abs(1.0 - Sym);
    V.Distance = ZeroPath;
    V.FromZero = true;
  }
}

Vector decode(Grid &Grid, const VectorD &V) {
  vector<int> MinDists;
  Grid[0][0].Distance = 0.0;
  for (int I = 1; I < Grid.size(); I++) {
    for (auto &Vertex : Grid[I]) {
      setMinDist(Grid, Vertex, V[I - 1]);
    }
  }
  Vector Res;
  auto Curr = Grid.back().back();
  while (Curr.PrevOne != -1 || Curr.PrevZero != -1) {
    if (Curr.FromZero) {
      Res.push_back(false);
      Curr = Grid[Curr.Layer - 1][Curr.PrevZero];
    } else {
      Res.push_back(true);
      Curr = Grid[Curr.Layer - 1][Curr.PrevOne];
    }
  }
  std::reverse(Res.begin(), Res.end());
  assert(Res.size() == V.size() && "Result has different size with source");
  return Res;
}

void decode(istream &IS, ostream &OS, int N, Grid &G) {
  VectorD V(N);
  for (int I = 0; I < N; ++I) {
    IS >> V[I];
  }
  auto Res = decode(G, V);
  for (auto It : Res) {
    OS << It << ' ';
  }
  OS << '\n';
}

inline static Vector operator*(const Vector &V, const Matrix &M) {
  Vector Result;
  for (int J = 0; J < M[0].size(); J++) {
    bool Curr = false;
    for (int I = 0; I < V.size(); I++) {
      Curr ^= (V[I] && M[I][J]);
    }
    Result.push_back(Curr);
  }
  return Result;
}

void encode(istream &IS, ostream &OS, int K, const Matrix &M) {
  Vector V(K);
  int Inp;
  for (int I = 0; I < K; ++I) {
    IS >> Inp;
    V[I] = Inp == 1;
  }
  auto Res = V * M;
  for (auto It : Res) {
    OS << It << ' ';
  }
  OS << '\n';
}

inline static void generateVector(Vector &V, std::mt19937 &Gen,
                                  uniform_int_distribution<int> &BinaryDist) {
  for (int I = 0; I < V.size(); I++) {
    V[I] = BinaryDist(Gen) == 1;
  }
}

void simulate(istream &IS, ostream &OS, const Matrix &G, int N, int K,
              Grid &Grid) {
  int NoiseLevel, Iterations, MaxErrors;
  IS >> NoiseLevel >> Iterations >> MaxErrors;

  std::random_device RD{};
  std::mt19937 Gen{RD()};
  LD Sigma = 0.5 * pow(10, -1.0 * NoiseLevel / 10) * (N / K);
  normal_distribution<long double> D{0, sqrt(Sigma)};

  uniform_int_distribution<int> BinaryDist(0, 1);

  Vector V(K);

  int CountErrors = 0;
  int It = 0;
  for (; It < Iterations; It++) {
    generateVector(V, Gen, BinaryDist);
    auto Encoded = V * G;
    VectorD NoisyEncoded(Encoded.size());
    for (int I = 0; I < Encoded.size(); I++) {
      auto Noise = D(Gen);
      NoisyEncoded[I] = 1 - 2 * Encoded[I] + Noise;
    }
    auto Decoded = decode(Grid, NoisyEncoded);
    if (Decoded != Encoded) {
      CountErrors++;
    }
    if (CountErrors == MaxErrors) {
      break;
    }
  }
  OS << 1.0 * CountErrors / It << '\n';
}

void decoderMain(istream &IS = cin, ostream &OS = cout) {
  int N, K;
  IS >> N >> K;

  Matrix G(K, Vector(N));

  int Inp;
  for (int I = 0; I < K; I++) {
    for (int J = 0; J < N; J++) {
      IS >> Inp;
      G[I][J] = Inp == 1;
    }
  }
  Matrix SpanForm = toSpanForm(G);
  auto Spans = findSpans(SpanForm);
  OS << "1 ";
  for (auto It : Spans) {
    OS << (1 << It.size()) << ' ';
  }
  OS << '\n';

  auto Grid = buildGrid(SpanForm, Spans, N);
  string Command;
  while (IS >> Command) {
    if (Command == "Decode") {
      decode(IS, OS, N, Grid);
    } else if (Command == "Encode") {
      encode(IS, OS, K, G);
    } else if (Command == "Simulate") {
      simulate(IS, OS, G, N, K, Grid);
    } else {
      OS << "FAILURE\n";
      return;
    }
  }
}

} // namespace

int main() {
  ifstream FileIS("input.txt");
  ofstream FileOS("output.txt");
  decoderMain(FileIS, FileOS);
}
