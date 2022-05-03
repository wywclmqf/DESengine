import os
import plotly.graph_objects as go
import plotly.io as pio
import plotly.express as px

pio.kaleido.scope.mathjax = None


fig = go.Figure()

fig.add_trace(go.Scatter(name="DesisCen", x=[2, 10, 50, 100, 500, 1000], mode='lines+markers'
                         , y=[2559297.68, 542460.64, 387496.47, 265739.65, 127043.45, 96959.26]
                         , line=dict(color='rgb(99,110,250)', width=2), marker=dict(size=5, symbol='circle')))
fig.add_trace(go.Scatter(name="DesisIC", x=[2, 10, 50, 100, 500, 1000], mode='lines+markers'
                         , y=[16416701.86, 3652568.167, 653433.52, 269476.34, 53337.96, 23324.5]
                         , line=dict(color='rgb(239,85,59)', width=2), marker=dict(size=5, symbol='square')))
fig.add_trace(go.Scatter(name="DesisSW", x=[2, 10, 50, 100, 500, 1000], mode='lines+markers'
                         , y=[28466171.92, 28132678.49, 28602392.21, 28115777.13, 27807011.55, 27521084.6]
                         , line=dict(color='rgb(255,161,90)', width=2), marker=dict(size=5, symbol='triangle-up')))
fig.add_trace(go.Scatter(name="Desis", x=[2, 10, 50, 100, 500, 1000], mode='lines+markers'
                         , y=[31580432.73, 29568188.2, 30965854.91, 29983225.03, 29512371.87, 30395262.19]
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
        ticktext=["0", "2M", "5M", "10M", "20M", "30M"],
        tickvals=[0, 2000000, 5000000, 10000000, 20000000, 30000000],
        tickmode="array",
        range=[0, 35000000],
    ),
    xaxis=dict(
        title_text="queries",
        titlefont=dict(size=15),
        ticktext=["2", "10", '100', "1000"],
        tickvals=[2, 10, 100, 1000],
        range=[0,3],
        type="log"
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
if not os.path.exists("E:\my paper\DesisPaper\Desis-Optimizing-Decentralized-Window-Aggregation\experiment\s2"):
    os.mkdir("E:\my paper\DesisPaper\Desis-Optimizing-Decentralized-Window-Aggregation\experiment\s2")

# fig.write_image("images/fig1.svg")
pio.write_image(fig, "E:\my paper\DesisPaper\Desis-Optimizing-Decentralized-Window-Aggregation\experiment\s2\shareWindow\punpun.pdf")
pio.write_image(fig, "E:\my paper\DesisPaper\Desis-Optimizing-Decentralized-Window-Aggregation\experiment\s2\shareWindow\punpun.svg")