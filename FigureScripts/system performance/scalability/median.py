import os
import plotly.graph_objects as go
import plotly.io as pio
import plotly.express as px

pio.kaleido.scope.mathjax = None


fig = go.Figure()

fig.add_trace(go.Scatter(name="DesisCen", x=[1, 2, 3, 4, 5, 6, 7, 8], mode='lines+markers'
                         , y=[1563426.905, 1559000.38, 1565001.108, 1558914.97, 1558007.2, 1555151.267, 1543917.05, 1566713.37]
                         , line=dict(color='rgb(99,110,250)', width=2), marker=dict(size=5, symbol='circle')))
fig.add_trace(go.Scatter(name="Disco", x=[1, 2, 3, 4, 5, 6, 7, 8], mode='lines+markers'
                         , y=[40379, 81010, 121524, 162212, 202755, 243282, 283521, 324008]
                         , line=dict(color='rgb(239,85,59)', width=2), marker=dict(size=5, symbol='square')))
fig.add_trace(go.Scatter(name="Desis", x=[1, 2, 3, 4, 5, 6, 7, 8], mode='lines+markers'
                         , y=[7301659.16, 7439741, 7531010, 7641958, 7664154, 7681207, 7689210, 7688381.22]
                         , line=dict(color='rgb(171,99,250)', width=2), marker=dict(size=5, symbol='cross')))

# fig.update_traces(marker_color='rgb(158,202,225)', marker_line_color='rgb(8,48,107)', marker_line_width=1.5, opacity=0.6,
# marker=dict(size=10, symbol='triangle-up'))))

#legend
fig.update_layout(
    # legend=dict(
    #     yanchor="top",
    #     y=0.99,
    #     xanchor="left",
    #     x=0.01,
    #     # bordercolor="Black",
    #     # borderwidth=2,
    #     # bgcolor="white",
    #     font=dict(
    #         size=10,
    #         color="black"
    #     ),
    # ),
    yaxis=dict(
        title_text="events/sec",
        titlefont=dict(size=15),
        # ticktext=["0", "3M", "5M", "10M", "100M", "300M"],
        # tickvals=[0, 3000000, 5000000, 10000000, 100000000, 300000/00],
        tickmode="array",
    ),
    xaxis=dict(
        title_text="local nodes",
        titlefont=dict(size=15),
        ticktext=["1", "2", "3", "4", "5", "6", "7", "8"],
        tickvals=[1, 2, 3, 4, 5, 6, 7, 8],
        range=[1,8],
        tickmode="array",
    )
)

# size & color
fig.update_layout(
    autosize=False,
    width=500,
    height=500,
    paper_bgcolor='rgba(0,0,0,0)',
    plot_bgcolor='rgba(0,0,0,0)'
)

# fig.update_yaxes(automargin=True)
# fig.update_yaxes(ticks="outside", tickwidth=1, tickcolor='black', ticklen=5)
fig.update_xaxes(showline=True, linewidth=1, linecolor='black', mirror=True)
fig.update_yaxes(showline=True, linewidth=1, linecolor='black', mirror=True)

fig.show()
if not os.path.exists("E:\my paper\DesisPaper\Desis-Optimizing-Decentralized-Window-Aggregation\experiment\s1"):
    os.mkdir("E:\my paper\DesisPaper\Desis-Optimizing-Decentralized-Window-Aggregation\experiment\s1")

# fig.write_image("images/fig1.svg")
pio.write_image(fig, "E:\my paper\DesisPaper\Desis-Optimizing-Decentralized-Window-Aggregation\experiment\s1\scalability\scalabilityM.pdf")
pio.write_image(fig, "E:\my paper\DesisPaper\Desis-Optimizing-Decentralized-Window-Aggregation\experiment\s1\scalability\scalabilityM.svg")